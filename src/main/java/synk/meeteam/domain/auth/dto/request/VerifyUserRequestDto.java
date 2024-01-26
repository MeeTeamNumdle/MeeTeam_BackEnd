package synk.meeteam.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record VerifyUserRequestDto(
        @NotNull String emailCode,
        @NotNull String nickName

        ) {
}
