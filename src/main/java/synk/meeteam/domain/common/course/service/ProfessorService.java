package synk.meeteam.domain.common.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.course.entity.Professor;
import synk.meeteam.domain.common.course.repository.ProfessorRepository;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    @Transactional
    public Professor createProfessor(Professor professor) {
        return professorRepository.save(professor);
    }
}
