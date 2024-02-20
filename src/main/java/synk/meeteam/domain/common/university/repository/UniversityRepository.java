package synk.meeteam.domain.common.university.repository;

import static synk.meeteam.domain.common.university.exception.UniversityExceptionType.NOT_FOUND_EMAIL_REGEX;
import static synk.meeteam.domain.common.university.exception.UniversityExceptionType.NOT_FOUND_UNIVERSITY_ID;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.exception.UniversityException;

public interface UniversityRepository extends JpaRepository<University, Long> {

    Optional<University> findByName(String name);

    default University findByNameOrElseThrowException(String name) {
        return findByName(name).orElseThrow(() -> new UniversityException(
                NOT_FOUND_EMAIL_REGEX));
    }

    Optional<University> findById(Long id);

    default University findByIdOrElseThrowException(Long universityId) {
        return findById(universityId).orElseThrow(() -> new UniversityException(
                NOT_FOUND_UNIVERSITY_ID));
    }
}
