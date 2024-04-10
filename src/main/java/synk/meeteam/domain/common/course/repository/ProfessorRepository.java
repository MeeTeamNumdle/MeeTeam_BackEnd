package synk.meeteam.domain.common.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.course.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorCustomRepository {
}
