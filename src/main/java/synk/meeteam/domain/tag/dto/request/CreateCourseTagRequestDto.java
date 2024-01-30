package synk.meeteam.domain.tag.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCourseTagRequestDto(
        @NotBlank Boolean isCourse,
        String courseTagName
) {
    public static CreateCourseTagRequestDto of(final Boolean isCourse, final String courseTagName){
        return new CreateCourseTagRequestDto(isCourse, courseTagName);
    }
}
