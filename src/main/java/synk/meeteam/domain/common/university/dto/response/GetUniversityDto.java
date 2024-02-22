package synk.meeteam.domain.common.university.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetUniversityDto(
        @Schema(description = "대학교 id", example = "1")
        Long universityId,
        @Schema(description = "대학교 이름", example = "광운대학교")
        String universityName,
        @Schema(description = "대학교 이메일 도메인", example = "kw.ac.kr")
        String universityRegex
) {
}
