package synk.meeteam.domain.auth.api;


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
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.auth.dto.request.AuthUserRequestDto;
import synk.meeteam.domain.auth.dto.request.SignUpUserRequestDto;
import synk.meeteam.domain.auth.dto.request.VerifyUserRequestDto;
import synk.meeteam.domain.auth.dto.response.AuthUserResponseDto;
import synk.meeteam.domain.auth.dto.response.LogoutUserResponseDto;
import synk.meeteam.domain.auth.dto.response.ReissueUserResponseDto;
import synk.meeteam.domain.auth.dto.response.SignUpUserResponseDto;
import synk.meeteam.domain.auth.service.AuthServiceProvider;
import synk.meeteam.domain.auth.service.vo.UserSignUpVO;
import synk.meeteam.domain.university.service.UniversityService;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.entity.enums.Role;
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
    private final UserService userService;

    @Value("${spring.security.oauth2.client.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.naver.redirect-uri}")
    private String redirectUri;

    @PostMapping("/social/login")
    public ResponseEntity<AuthUserResponseDto> login(
            @RequestHeader(value = "authorization-code") final String authorizationCode,
            @RequestBody @Valid final
            AuthUserRequestDto request) {

        UserSignUpVO vo = authServiceProvider.getAuthService(request.platformType())
                .saveUserOrLogin(authorizationCode, request);

        if (vo.role() == Role.GUEST) {
            return ResponseEntity.ok(AuthUserResponseDto
                    .of(vo.platformId(), vo.authType(), vo.name(), vo.role(), null, null));
        }

        AuthUserResponseDto responseDTO = jwtService.issueToken(vo);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/social/sign-up")
    public ResponseEntity<SignUpUserResponseDto> signUp(
            @RequestBody @Valid SignUpUserRequestDto requestDto
    ) {
        Long universityId = universityService.getUniversityId(requestDto.universityName(), requestDto.departmentName(),
                requestDto.email());

        userService.updateUniversityInfo(requestDto, universityId);
        mailService.sendMail(requestDto, requestDto.platformId());

        return ResponseEntity.ok(SignUpUserResponseDto.of(requestDto.platformId()));
    }

    @PostMapping("/email-verify")
    public ResponseEntity<AuthUserResponseDto> verify(
            @RequestBody @Valid VerifyUserRequestDto requestDto) {

        User user = mailService.verify(requestDto.emailCode(), requestDto.nickName());
        UserSignUpVO vo = UserSignUpVO.of(user, user.getPlatformType(), user.getRole(), AuthType.SIGN_UP);
        AuthUserResponseDto responseDTO = jwtService.issueToken(vo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueUserResponseDto> reissue(HttpServletRequest request) {
        ReissueUserResponseDto reissueUserResponseDto = jwtService.reissueToken(request);
        return ResponseEntity.ok().body(reissueUserResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutUserResponseDto> logout(HttpServletRequest request) {
        return ResponseEntity.ok(jwtService.logout(request));
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
