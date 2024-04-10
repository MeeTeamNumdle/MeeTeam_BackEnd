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
import synk.meeteam.domain.common.course.repository.ProfessorRepository;
import synk.meeteam.domain.common.course.service.ProfessorService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private ProfessorRepository professorRepository;

    @Test
    public void 교수명목록조회_교수명목록DTO반환() {
        //given
        String keyword = "김";
        long limit = 3;

        doReturn(CourseFixture.createProfessorDtos())
                .when(professorRepository).findAllByKeywordAndTopLimit(keyword, limit);

        //when
        List<SearchCourseDto> professorDto = professorService.searchByKeyword(keyword, limit);

        //then
        assertThat(professorDto).extracting("name").containsExactly("김용혁");
    }
}
