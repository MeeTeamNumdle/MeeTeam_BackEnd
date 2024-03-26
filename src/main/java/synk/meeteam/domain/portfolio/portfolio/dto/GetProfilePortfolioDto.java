package synk.meeteam.domain.portfolio.portfolio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record GetProfilePortfolioDto(
        @Schema(description = "포트폴리오 id", example = "1")
        Long id,
        @Schema(description = "제목", example = "Meeteam")
        String title,
        @Schema(description = "메인 커버이미지 url", example = "https://~~")
        String mainImageUrl,
        @Schema(description = "시작일")
        LocalDate startDate,
        @Schema(description = "종료일")
        LocalDate endDate,
        @Schema(description = "분야", example = "개발")
        String field,
        @Schema(description = "역할", example = "백엔드 개발자")
        String role
) {
}
