package synk.meeteam.domain.auth.api.dto.response;

import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.entity.enums.Role;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

public record MemberAuthResponseDTO(@NotNull String platformId, @NotNull AuthType authType, @NotNull String userName,
                                    @NotNull Role role, String accessToken, String refreshToken) {
    public static MemberAuthResponseDTO of(String platformId, AuthType authType, String userName, Role role,
                                           String accessToken, String refreshToken) {
        return new MemberAuthResponseDTO(platformId, authType, userName, role, accessToken, refreshToken);
    }
}
