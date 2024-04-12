package synk.meeteam.domain.common.course.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;
import synk.meeteam.domain.common.course.entity.Course;
import synk.meeteam.domain.common.course.repository.CourseRepository;
import synk.meeteam.domain.common.university.entity.University;

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

    @Transactional
    public Course getOrCreateCourse(String courseName, University university) {
        Course course = courseRepository.findByNameAndUniversity(courseName, university).orElse(null);
        if (course == null) {
            course = createCourse(courseName, university);
            courseRepository.save(course);
        }

        return course;
    }

    private Course createCourse(String courseName, University university) {
        return Course.builder()
                .name(courseName)
                .university(university)
                .build();
    }
}
