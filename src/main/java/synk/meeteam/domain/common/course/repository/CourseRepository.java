package synk.meeteam.domain.common.course.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.course.entity.Course;
import synk.meeteam.domain.common.university.entity.University;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseCustomRepository {

    Optional<Course> findByNameAndUniversity(String courseName, University university);
}
