package synk.meeteam.domain.auth.service;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.infra.oauth.service.NaverAuthService;

@Component
@RequiredArgsConstructor
public class AuthServiceProvider {
    private static final ConcurrentHashMap<PlatformType, AuthService> authServiceMap = new ConcurrentHashMap<>();

    private final NaverAuthService naverAuthService;

    @PostConstruct
    void init() {
        authServiceMap.put(PlatformType.NAVER, naverAuthService);
    }

    public AuthService getAuthService(PlatformType platformType) {
        return authServiceMap.get(platformType);
    }
}
