package synk.meeteam.domain.common.course.repository;

import static synk.meeteam.domain.common.course.entity.QProfessor.professor;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.common.course.dto.QSearchCourseDto;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;

@Repository
@RequiredArgsConstructor
public class ProfessorCustomRepositoryImpl implements ProfessorCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SearchCourseDto> findAllByKeywordAndTopLimit(String keyword, long limit) {
        return queryFactory
                .select(new QSearchCourseDto(professor.id, professor.name))
                .from(professor)
                .where(professor.name.startsWith(keyword))
                .limit(limit)
                .orderBy(professor.name.asc().nullsLast())
                .fetch();
    }
}
