package synk.meeteam.domain.auth.api.dto.response;

import jakarta.validation.constraints.NotNull;

public record UserReissueResponseDTO(@NotNull String platformId, @NotNull String accessToken, @NotNull String refreshToken) {
    public static UserReissueResponseDTO of(String platformId, String accessToken,
                                            String refreshToken) {
        return new UserReissueResponseDTO(platformId, accessToken, refreshToken);
    }
}
