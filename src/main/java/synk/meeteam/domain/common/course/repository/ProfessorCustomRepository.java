package synk.meeteam.domain.common.course.repository;

import java.util.List;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;

public interface ProfessorCustomRepository {
    List<SearchCourseDto> findAllByKeywordAndTopLimit(String keyword, long limit);

}
