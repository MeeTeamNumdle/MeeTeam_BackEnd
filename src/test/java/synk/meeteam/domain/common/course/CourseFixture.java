package synk.meeteam.domain.common.course;

import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;
import synk.meeteam.domain.common.course.entity.Course;

public class CourseFixture {
    public static List<SearchCourseDto> createCourseDtos() {
        List<SearchCourseDto> courseDtos = new ArrayList<>();
        courseDtos.add(new SearchCourseDto(1L, "응용소프트웨어실습"));
        return courseDtos;
    }

    public static List<SearchCourseDto> createProfessorDtos() {
        List<SearchCourseDto> professorDtos = new ArrayList<>();
        professorDtos.add(new SearchCourseDto(1L, "김용혁"));
        return professorDtos;
    }

    public static Course createCoureName(String name) {
        return Course.builder()
                .name(name)
                .build();
    }

    public static Course createProfessor(String name) {
        return Course.builder()
                .name(name)
                .build();
    }

}
