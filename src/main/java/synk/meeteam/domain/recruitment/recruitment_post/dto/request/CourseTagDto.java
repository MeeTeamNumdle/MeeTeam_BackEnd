package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CourseTagDto(
        @NotNull
        @Schema(description = "수업 여부", example = "true")
        Boolean isCourse,
        @Schema(description = "수업 관련 태그 이름", example = "응용소프트웨어실습 or null")
        String courseTagName,
        @Schema(description = "교수명", example = "김용혁 or null")
        String courseProfessor
) {
}
