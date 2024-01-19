package synk.meeteam.domain.auth.api;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_MAIL_REGEX;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.auth.api.dto.request.UserAuthRequestDTO;
import synk.meeteam.domain.auth.api.dto.request.UserSignUpRequestDTO;
import synk.meeteam.domain.auth.api.dto.response.UserAuthResponseDTO;
import synk.meeteam.domain.auth.api.dto.response.UserReissueResponseDTO;
import synk.meeteam.domain.auth.api.dto.response.UserSignUpResponseDTO;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.auth.service.AuthServiceProvider;
import synk.meeteam.domain.auth.service.vo.UserSignUpVO;
import synk.meeteam.domain.university.service.UniversityService;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.entity.enums.Role;
import synk.meeteam.domain.user.repository.UserRepository;
import synk.meeteam.domain.user.service.UserService;
import synk.meeteam.infra.mail.MailService;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;
import synk.meeteam.security.jwt.service.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthServiceProvider authServiceProvider;
    private final JwtService jwtService;
    private final MailService mailService;
    private final UniversityService universityService;
    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${spring.security.oauth2.client.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.naver.redirect-uri}")
    private String redirectUri;

    @PostMapping("/social/login")
    public ResponseEntity<UserAuthResponseDTO> login(
            @RequestHeader(value = "authorization-code") final String authorizationCode,
            @RequestBody @Valid final
            UserAuthRequestDTO request, HttpServletResponse response) {

        UserSignUpVO vo = authServiceProvider.getAuthService(request.platformType())
                .saveUserOrLogin(authorizationCode, request);

        if (vo.role() == Role.GUEST) {
            return ResponseEntity.ok(UserAuthResponseDTO
                    .of(vo.platformId(), vo.authType(), vo.name(), vo.role(), null, null));
        }

        UserAuthResponseDTO responseDTO = jwtService.issueToken(vo);
        if (responseDTO.authType().equals(AuthType.SIGN_UP)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(responseDTO);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/social/sign-up")
    public ResponseEntity<UserSignUpResponseDTO> signUp(
            @RequestBody @Valid UserSignUpRequestDTO requestDTO
    ) {
        if (!universityService.isValidRegex(requestDTO.universityName(), requestDTO.email())){
            throw new AuthException(INVALID_MAIL_REGEX);
        }

        userService.updateUniversityInfo(requestDTO);
        mailService.sendMail(requestDTO);

        return ResponseEntity.ok(UserSignUpResponseDTO.of(requestDTO.platformId()));
    }

    @GetMapping("/email-verify")
    public ResponseEntity<UserAuthResponseDTO> verify(
            @RequestParam String emailCode) {

        User user = mailService.verify(emailCode);
        UserSignUpVO vo = UserSignUpVO.of(user, user.getPlatformType(), user.getRole(), AuthType.SIGN_UP);
        UserAuthResponseDTO responseDTO = jwtService.issueToken(vo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/reissue")
    public ResponseEntity<UserReissueResponseDTO> reissue(HttpServletRequest request,
                                                          HttpServletResponse response) {
        UserReissueResponseDTO userReissueResponseDTO = jwtService.reissueToken(request, response);
        return ResponseEntity.ok().body(userReissueResponseDTO);
    }


    @GetMapping("/authTest")
    public String authTest(HttpServletRequest request, HttpServletResponse response) {
        String redirectURL = "https://nid.naver.com/oauth2.0/authorize?client_id=" + clientId
                + "&redirect_uri=" + redirectUri + "&response_type=code";
        try {
            response.sendRedirect(
                    redirectURL);
        } catch (Exception e) {
            log.info("authTest = {}", e);
        }

        return "SUCCESS";
    }
}
