package synk.meeteam.domain.common.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
}
