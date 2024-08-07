package synk.meeteam.domain.common.course.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;
import synk.meeteam.domain.common.course.entity.Professor;
import synk.meeteam.domain.common.course.repository.ProfessorRepository;
import synk.meeteam.domain.common.university.entity.University;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    @Transactional
    public Professor createProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    @Transactional(readOnly = true)
    public List<SearchCourseDto> searchByKeyword(String keyword, long limit) {
        return professorRepository.findAllByKeywordAndTopLimit(keyword, limit);
    }

    @Transactional
    public Professor getOrCreateProfessor(String professorName, University university) {
        Professor professor = professorRepository.findByNameAndUniversity(professorName, university).orElse(null);
        if (professor == null) {
            professor = createProfessor(professorName, university);
            professorRepository.save(professor);
        }

        return professor;
    }

    private Professor createProfessor(String professorName, University university) {
        return Professor.builder()
                .name(professorName)
                .university(university)
                .build();
    }
}
