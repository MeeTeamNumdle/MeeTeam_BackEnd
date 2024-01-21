package synk.meeteam.domain.university.repository;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.NOT_FOUND_UNIVERSITY_AND_DEPARTMENT;
import static synk.meeteam.domain.university.exception.UniversityExceptionType.NOT_FOUND_EMAIL_REGEX;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.university.entity.University;
import synk.meeteam.domain.university.exception.UniversityException;

public interface UniversityRepository extends JpaRepository<University, Long> {

    Optional<University> findByUniversityName(String universityName);

    default University findByUniversityNameOrElseThrowException(String universityName) {
        return findByUniversityName(universityName).orElseThrow(() -> new UniversityException(NOT_FOUND_EMAIL_REGEX));
    }

    Optional<University> findByUniversityNameAndDepartmentName(String universityName, String departmentName);

    default University findByUniversityNameAndDepartmentNameOrElseThrowException(String universityName,
                                                                                 String departmentName) {
        return findByUniversityNameAndDepartmentName(universityName, departmentName).orElseThrow(
                () -> new AuthException(NOT_FOUND_UNIVERSITY_AND_DEPARTMENT));
    }
}
