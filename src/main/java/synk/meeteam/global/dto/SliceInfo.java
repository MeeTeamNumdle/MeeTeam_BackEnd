package synk.meeteam.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SliceInfo(
        @Schema(description = "현재 페이지", example = "1")
        int page,
        @Schema(description = "개수", example = "10")
        int size,
        @Schema(description = "다음 페이지 존재 여부", example = "true")
        boolean hasNextPage
) {
}
