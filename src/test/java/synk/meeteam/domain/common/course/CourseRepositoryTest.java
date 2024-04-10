package synk.meeteam.domain.common.course;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;
import synk.meeteam.domain.common.course.repository.CourseRepository;

@DataJpaTest
@ActiveProfiles("test")
public class CourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void 강의명목록조회_강의명DTO반환_keyword는공백일때() {
        //given
        String keyword = "";
        long limit = 5;
        //when
        List<SearchCourseDto> courses = courseRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(courses).extracting("name").containsExactlyInAnyOrder(
                "응용소프트웨어실습");
        assertThat(courses.size()).isEqualTo(1);
    }

    @Test
    void 강의명목록조회_강의명DTO반환_keyword는운영체제일때() {
        //given
        String keyword = "응용";
        long limit = 5;
        //when
        List<SearchCourseDto> courses = courseRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(courses).extracting("name").contains("응용소프트웨어실습");
        assertThat(courses.size()).isEqualTo(1);
    }


}
