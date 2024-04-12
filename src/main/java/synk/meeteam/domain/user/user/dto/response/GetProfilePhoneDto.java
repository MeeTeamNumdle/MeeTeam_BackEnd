package synk.meeteam.domain.user.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetProfilePhoneDto(
        @Schema(description = "전화번호", example = "010-1234-5678")
        String content,
        @Schema(description = "공개여부", example = "true")
        boolean isPublic
) {

}
