package synk.meeteam.domain.recruitment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateRecruitmentCourseRequestDto(
        @NotBlank Boolean isCourse,
        String courseTagName
) {
}
