package synk.meeteam.domain.common.course.repository;

import static synk.meeteam.domain.common.course.entity.QCourse.course;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.common.course.dto.QSearchCourseDto;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;

@Repository
@RequiredArgsConstructor
public class CourseCustomRepositoryImpl implements CourseCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SearchCourseDto> findAllByKeywordAndTopLimit(String keyword, long limit) {
        return queryFactory
                .select(new QSearchCourseDto(course.id, course.name))
                .from(course)
                .where(course.name.startsWith(keyword))
                .limit(limit)
                .orderBy(course.name.length().asc().nullsLast())
                .fetch();
    }
}
