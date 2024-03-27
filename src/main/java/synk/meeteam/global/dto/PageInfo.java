package synk.meeteam.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PageInfo(
        @Schema(description = "현재 페이지", example = "1")
        Long page,
        @Schema(description = "개수", example = "10")
        Long size,
        @Schema(description = "모든 개수", example = "30")
        Long totalContents,
        @Schema(description = "전체 페이지 수", example = "3")
        Long totalPages
) {
}
