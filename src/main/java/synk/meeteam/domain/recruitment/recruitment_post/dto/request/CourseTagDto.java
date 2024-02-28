package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CourseTagDto(
        @NotBlank
        @Schema(description = "수업 여부", example = "true")
        Boolean isCourse,
        @Schema(description = "수업 관련 태그 이름", example = "응용소프트웨어실습")
        String courseTagName,
        @Schema(description = "교수명", example = "응용소프트웨어실습")
        String courseProfessor
) {
}
