package synk.meeteam.domain.auth.api.dto.response;

import jakarta.validation.constraints.NotNull;

public record UserSignUpResponseDTO(
        @NotNull String  platformId
        ) {
    public static UserSignUpResponseDTO of(String platformId) {
        return new UserSignUpResponseDTO(platformId);
    }
}
