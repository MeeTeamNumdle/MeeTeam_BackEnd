package synk.meeteam.domain.common.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    @Transactional(readOnly = true)
    public String getEmail(Long id, String emailId) {
        University university = universityRepository.findByIdOrElseThrowException(id);

        String emailFormat = "%s@%s";
        return String.format(emailFormat, emailId, university.getEmailRegex());
    }
}
