package synk.meeteam.domain.auth.dto.response;

import jakarta.validation.constraints.NotNull;

public record SignUpUserResponseDto(
        @NotNull String  platformId
        ) {
    public static SignUpUserResponseDto of(String platformId) {
        return new SignUpUserResponseDto(platformId);
    }
}
