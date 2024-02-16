package synk.meeteam.domain.common.university.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.exception.UniversityException;
import synk.meeteam.domain.common.university.exception.UniversityExceptionType;

public interface UniversityRepository extends JpaRepository<University, Long> {

    Optional<University> findByUniversityName(String universityName);

    default University findByUniversityNameOrElseThrowException(String universityName) {
        return findByUniversityName(universityName).orElseThrow(() -> new UniversityException(
                UniversityExceptionType.NOT_FOUND_EMAIL_REGEX));
    }

    Optional<University> findByUniversityNameAndDepartmentName(String universityName, String departmentName);

    default University findByUniversityNameAndDepartmentNameOrElseThrowException(String universityName,
                                                                                 String departmentName) {
        return findByUniversityNameAndDepartmentName(universityName, departmentName).orElseThrow(
                () -> new AuthException(UniversityExceptionType.NOT_FOUND_UNIVERSITY_AND_DEPARTMENT));
    }

    Optional<University> findById(Long universityId);

    default University findByIdOrElseThrowException(Long universityId) {
        return findById(universityId).orElseThrow(() -> new UniversityException(
                UniversityExceptionType.NOT_FOUND_UNIVERSITY_ID));
    }
}
