package synk.meeteam.domain.common.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
