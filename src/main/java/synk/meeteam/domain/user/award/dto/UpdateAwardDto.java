package synk.meeteam.domain.user.award.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "UpdateAwardDto", description = "수상/활동 내역 Dto")
public record UpdateAwardDto(
        @Schema(description = "제목", example = "2022 공공데이터 활용 공모전")
        String title,
        @Schema(description = "부연설명", example = "장려상 수상")
        String description,

        @Schema(description = "기간 시작일", example = "2022-08")
        LocalDate startDate,

        @Schema(description = "기간 종료일", example = "2022-12")
        LocalDate endDate
) {
}
