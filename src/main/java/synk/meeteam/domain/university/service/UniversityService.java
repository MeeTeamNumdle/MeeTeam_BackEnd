package synk.meeteam.domain.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.university.entity.University;
import synk.meeteam.domain.university.repository.UniversityRepository;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    public boolean isValidRegex(String universityName, String email){
        University foundUniversity = universityRepository.findByUniversityNameOrElseThrowException(universityName);
        String regex = extractEmailRegex(email);

        if(!foundUniversity.getEmailRegex().equals(regex)){
            return false;
        }

        return true;
    }

    private String extractEmailRegex(String email){
        String[] split = email.split("@");
        return split[1];
    }
}
