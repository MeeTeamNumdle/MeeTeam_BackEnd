package synk.meeteam.domain.common.course.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.course.entity.Professor;
import synk.meeteam.domain.common.university.entity.University;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorCustomRepository {

    Optional<Professor> findByNameAndUniversity(String professorName, University university);
}
