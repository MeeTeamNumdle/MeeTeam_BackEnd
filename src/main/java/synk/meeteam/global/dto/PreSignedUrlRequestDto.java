package synk.meeteam.global.dto;

import jakarta.validation.constraints.NotNull;

public record PreSignedUrlRequestDto(
        @NotNull
        String fileName
) {
}
