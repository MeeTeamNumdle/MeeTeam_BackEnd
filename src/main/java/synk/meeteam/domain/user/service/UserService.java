package synk.meeteam.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.auth.api.dto.request.UserSignUpRequestDTO;
import synk.meeteam.domain.university.entity.University;
import synk.meeteam.domain.university.repository.UniversityRepository;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    public void updateUniversityInfo(UserSignUpRequestDTO requestDTO) {
        University university = universityRepository.findByUniversityNameAndDepartmentNameOrElseThrowException(
                requestDTO.universityName(),
                requestDTO.departmentName());

        User foundUser = userRepository.findByPlatformIdAndPlatformTypeOrElseThrowException(
                requestDTO.platformId(), requestDTO.platformType());
        foundUser.updateEmail(requestDTO.email());
        foundUser.updateAdmissionYear(requestDTO.admissionYear());
        foundUser.updateUniversity(university);
        userRepository.save(foundUser);
    }
}
