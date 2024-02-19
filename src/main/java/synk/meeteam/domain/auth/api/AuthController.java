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
import synk.meeteam.domain.common.university.service.UniversityService;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.UserVO;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.infra.mail.MailService;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;
import synk.meeteam.security.AuthUser;
import synk.meeteam.security.jwt.service.JwtService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController implements AuthApi {
    private final AuthServiceProvider authServiceProvider;
    private final JwtService jwtService;
    private final MailService mailService;
    private final UniversityService universityService;
    private final UserService userService;

    @Value("${spring.security.oauth2.client.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.naver.redirect-uri}")
    private String redirectUri;

    @Override
    @PostMapping("/social/login")
    public ResponseEntity<AuthUserResponseDto> login(
            @RequestHeader(value = "authorization-code") final String authorizationCode,
            @RequestBody @Valid final
            AuthUserRequestDto requestDto) {

        UserSignUpVO vo = authServiceProvider.getAuthService(requestDto.platformType())
                .saveUserOrLogin(authorizationCode, requestDto);

        if (vo.authority() == Authority.GUEST) {
            return ResponseEntity.ok(AuthUserResponseDto
                    .of(vo.platformId(), vo.authType(), vo.name(), vo.authority(), null, null));
        }

        AuthUserResponseDto responseDTO = jwtService.issueToken(vo);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    @PostMapping("/social/email-verify")
    public ResponseEntity<SignUpUserResponseDto> createTempUserAndSendEmail(
            @RequestBody @Valid SignUpUserRequestDto requestDto
    ) {
        String email = universityService.getEmail(requestDto.universityId(), requestDto.emailId());
        authServiceProvider.getAuthService(requestDto.platformType()).updateUniversityInfo(requestDto, email);
        mailService.sendMail(requestDto.platformId(), email);

        return ResponseEntity.ok(SignUpUserResponseDto.of(requestDto.platformId()));
    }

    @Override
    @PostMapping("/sign-up")
    public ResponseEntity<AuthUserResponseDto> signUp(
            @RequestBody @Valid VerifyUserRequestDto requestDto) {

        UserVO userVO = mailService.verify(requestDto.emailCode());
        User user = authServiceProvider.getAuthService(userVO.getPlatformType())
                .createSocialUser(userVO, requestDto.nickName());

        UserSignUpVO vo = UserSignUpVO.of(user, user.getPlatformType(), user.getAuthority(), AuthType.SIGN_UP);
        AuthUserResponseDto responseDTO = jwtService.issueToken(vo);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Override
    @PostMapping("/reissue")
    public ResponseEntity<ReissueUserResponseDto> reissue(HttpServletRequest request) {
        ReissueUserResponseDto reissueUserResponseDto = jwtService.reissueToken(request);
        return ResponseEntity.ok().body(reissueUserResponseDto);
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<LogoutUserResponseDto> logout(@AuthUser final User user) {
        return ResponseEntity.ok(jwtService.logout(user));
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
