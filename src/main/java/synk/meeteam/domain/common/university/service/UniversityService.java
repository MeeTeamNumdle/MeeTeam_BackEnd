package synk.meeteam.domain.common.university.service;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_MAIL_REGEX;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    public Long getUniversityId(String universityName, String departmentName, String email) {
        University foundUniversity = universityRepository.findByUniversityNameAndDepartmentNameOrElseThrowException(
                universityName, departmentName);

        String regex = extractEmailRegex(email);

        if (!foundUniversity.getEmailRegex().equals(regex)) {
            throw new AuthException(INVALID_MAIL_REGEX);
        }

        return foundUniversity.getId();
    }

    private String extractEmailRegex(String email) {
        String[] split = email.split("@");
        return split[1];
    }
}
