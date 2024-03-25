package synk.meeteam.domain.user.award.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetProfileAwardDto(
        @Schema(description = "제목", example = "2022 공공데이터 활용 공모전")
        String title,
        @Schema(description = "기간 시작일")
        String startDate,
        @Schema(description = "기간 종료일")
        String endDate,
        @Schema(description = "부연설명", example = "장려상 수상")
        String description
) {


}
