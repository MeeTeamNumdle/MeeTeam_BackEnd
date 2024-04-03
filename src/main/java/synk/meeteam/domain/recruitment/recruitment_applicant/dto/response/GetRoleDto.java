package synk.meeteam.domain.recruitment.recruitment_applicant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetRoleDto(
        @Schema(description = "역할 id", example = "1")
        Long id,

        @Schema(description = "역할 이름", example = "백엔드개발자")
        String title
) {
}
