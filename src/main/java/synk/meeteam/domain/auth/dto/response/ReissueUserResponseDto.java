package synk.meeteam.domain.auth.dto.response;

import jakarta.validation.constraints.NotNull;

public record ReissueUserResponseDto(@NotNull String platformId, @NotNull String accessToken, @NotNull String refreshToken) {
    public static ReissueUserResponseDto of(String platformId, String accessToken,
                                            String refreshToken) {
        return new ReissueUserResponseDto(platformId, accessToken, refreshToken);
    }
}
