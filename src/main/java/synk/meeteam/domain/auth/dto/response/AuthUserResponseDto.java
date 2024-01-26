package synk.meeteam.domain.auth.dto.response;

import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.entity.enums.Role;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

public record AuthUserResponseDto(@NotNull String platformId, @NotNull AuthType authType, @NotNull String userName,
                                  @NotNull Role role, String accessToken, String refreshToken) {
    public static AuthUserResponseDto of(String platformId, AuthType authType, String userName, Role role,
                                         String accessToken, String refreshToken) {
        return new AuthUserResponseDto(platformId, authType, userName, role, accessToken, refreshToken);
    }
}
