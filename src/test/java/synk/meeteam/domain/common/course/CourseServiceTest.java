package synk.meeteam.domain.common.course;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;
import synk.meeteam.domain.common.course.repository.CourseRepository;
import synk.meeteam.domain.common.course.service.CourseService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CourseServiceTest {
    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void 강의명목록조회_강의명목록DTO반환() {
        //given
        String keyword = "응용소프트웨어실습";
        long limit = 3;

        doReturn(CourseFixture.createCourseDtos()).when(courseRepository).findAllByKeywordAndTopLimit(keyword, limit);

        //when
        List<SearchCourseDto> courseDtos = courseService.searchByKeyword(keyword, limit);

        //then
        assertThat(courseDtos).extracting("name").containsExactly("응용소프트웨어실습");
    }


}
