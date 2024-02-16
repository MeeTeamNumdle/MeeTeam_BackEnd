package synk.meeteam.infra.oauth.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import synk.meeteam.domain.auth.dto.request.AuthUserRequestDto;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.auth.exception.AuthExceptionType;
import synk.meeteam.domain.auth.service.AuthService;
import synk.meeteam.domain.auth.service.vo.UserSignUpVO;
import synk.meeteam.domain.common.university.repository.UniversityRepository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.enums.Role;
import synk.meeteam.domain.user.user.repository.UserRepository;
import synk.meeteam.infra.oauth.service.vo.NaverMemberVO;
import synk.meeteam.infra.oauth.service.vo.NaverTokenVO;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;
import synk.meeteam.infra.redis.repository.RedisUserRepository;

@Service
@Transactional(readOnly = true)
public class NaverAuthService extends AuthService {

    @Value("${spring.security.oauth2.client.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.naver.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.naver.state}")
    private String state;
    @Value("${spring.security.oauth2.client.naver.user-info-uri}")
    private String userInfoUri;
    @Value("${spring.security.oauth2.client.naver.token-uri.host}")
    private String tokenUriHost;
    @Value("${spring.security.oauth2.client.naver.token-uri.path}")
    private String tokenUriPath;

    public NaverAuthService(UserRepository userRepository, RedisUserRepository redisUserRepository, UniversityRepository universityRepository) {
        super(userRepository, redisUserRepository, universityRepository);
    }

    @Override
    public UserSignUpVO saveUserOrLogin(String authorizationCode, AuthUserRequestDto request) {
        String accessToken = getAccessToken(authorizationCode, clientId, clientSecret, state).getAccess_token();
        NaverMemberVO naverMemberInfo = getNaverUserInfo(accessToken);
        User foundUser = getUser(request.platformType(), naverMemberInfo.getResponse().getId());

        if (foundUser != null) {
            return UserSignUpVO.of(foundUser, request.platformType(), Role.USER, AuthType.LOGIN);
        }

        // redis 사용, 무조건 새로 회원가입하는 경우
        User savedUser = saveTempUser(request, naverMemberInfo.getResponse().getEmail(),
                naverMemberInfo.getResponse().getName(), naverMemberInfo.getResponse().getId(),
                naverMemberInfo.getResponse().getMobile());

        return UserSignUpVO.of(savedUser, request.platformType(), Role.GUEST, AuthType.SIGN_UP);

    }

    public NaverMemberVO getNaverUserInfo(String accessToken) {
        WebClient webClient = WebClient.create();
        try {
            return webClient.post()
                    .uri(userInfoUri)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(NaverMemberVO.class)
                    .block();
        } catch (Exception e) {
            throw new AuthException(AuthExceptionType.INVALID_MEMBER_PLATFORM_AUTHORIZATION_CODE);
        }
    }

    private NaverTokenVO getAccessToken(String authorizationCode, String clientId, String clientSecret, String state) {
        WebClient webClient = WebClient.builder().build();
        return webClient.post()
                .uri(uriBuilder ->
                        uriBuilder
                                .scheme("https")  // 스킴을 명시적으로 지정
                                .host(tokenUriHost)  // 호스트를 명시적으로 지정
                                .path(tokenUriPath)
                                .queryParam("grant_type", "authorization_code")
                                .queryParam("client_id", clientId)
                                .queryParam("client_secret", clientSecret)
                                .queryParam("code", authorizationCode)
                                .queryParam("state", state)
                                .build()
                )
                .retrieve()
                .bodyToMono(NaverTokenVO.class)
                .block();
    }
}