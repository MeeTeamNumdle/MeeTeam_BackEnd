package synk.meeteam.domain.common.course.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;
import synk.meeteam.domain.common.course.entity.Course;
import synk.meeteam.domain.common.course.repository.CourseRepository;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public Course createCourse(Course courses) {
        return courseRepository.save(courses);
    }

    @Transactional(readOnly = true)
    public List<SearchCourseDto> searchByKeyword(String keyword, long limit) {
        return courseRepository.findAllByKeywordAndTopLimit(keyword, limit);
    }
}
