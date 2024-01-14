package synk.meeteam.domain.auth.api.dto.response;

import jakarta.validation.constraints.NotNull;

public record MemberReissueResponseDTO(@NotNull String platformId, @NotNull String accessToken, @NotNull String refreshToken) {
    public static MemberReissueResponseDTO of(String platformId, String accessToken,
                                           String refreshToken) {
        return new MemberReissueResponseDTO(platformId, accessToken, refreshToken);
    }
}
