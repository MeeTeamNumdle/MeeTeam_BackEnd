package synk.meeteam.domain.user.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePhoneNumber(
        @Schema(description = "전화번호", example = "010-1234-1234")
        String content,
        @Schema(description = "전화번호 공개 여부", example = "false")
        boolean isPublic
) {
}
