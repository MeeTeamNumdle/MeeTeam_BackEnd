package synk.meeteam.domain.common.course;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;
import synk.meeteam.domain.common.course.repository.ProfessorRepository;

@DataJpaTest
@ActiveProfiles("test")
public class ProfessorRepositoryTest {
    @Autowired
    ProfessorRepository professorRepository;

    @Test
    void 교수명목록조회_교수명DTO반환_keyword는공백일때() {
        //given
        String keyword = "";
        long limit = 5;
        //when
        List<SearchCourseDto> professors = professorRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(professors).extracting("name").containsExactlyInAnyOrder(
                "김용혁");
        assertThat(professors.size()).isEqualTo(1);
    }

    @Test
    void 교수명목록조회_교수명DTO반환_keyword는문일때() {
        //given
        String keyword = "김";
        long limit = 5;
        //when
        List<SearchCourseDto> professors = professorRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(professors).extracting("name").containsExactly("김용혁");
        assertThat(professors.size()).isEqualTo(1);
    }
}
