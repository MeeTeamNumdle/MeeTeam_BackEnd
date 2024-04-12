package synk.meeteam.domain.user.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetProfileEmailDto(
        @Schema(description = "이메일", example = "skqnrua5123@kw.ac.kr")
        String content,
        @Schema(description = "공개여부", example = "true")
        boolean isPublic,
        @Schema(description = "주 이메일", example = "true")
        boolean isDefault
) {
}
