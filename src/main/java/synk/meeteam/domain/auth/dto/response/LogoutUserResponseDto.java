package synk.meeteam.domain.auth.dto.response;

import jakarta.validation.constraints.NotNull;

public record LogoutUserResponseDto(
        @NotNull String PlatformId
) {
    public static LogoutUserResponseDto of(String platformId) {
        return new LogoutUserResponseDto(platformId);
    }
}
