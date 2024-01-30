package synk.meeteam.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.auth.dto.request.AuthUserRequestDto;
import synk.meeteam.domain.auth.dto.request.SignUpUserRequestDto;
import synk.meeteam.domain.auth.service.vo.UserSignUpVO;
import synk.meeteam.domain.university.entity.University;
import synk.meeteam.domain.university.repository.UniversityRepository;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.entity.UserVO;
import synk.meeteam.domain.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.entity.enums.Role;
import synk.meeteam.domain.user.repository.UserRepository;
import synk.meeteam.infra.redis.repository.RedisUserRepository;

@Service
@RequiredArgsConstructor
public abstract class AuthService {
    private final UserRepository userRepository;
    private final RedisUserRepository redisUserRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public abstract UserSignUpVO saveUserOrLogin(String platformType, AuthUserRequestDto request);

    protected User getUser(PlatformType platformType, String platformId) {
        return userRepository.findByPlatformIdAndPlatformType(platformId, platformType)
                .orElse(null);
    }

    protected User saveTempUser(AuthUserRequestDto request, String email, String name, String id,
                            String phoneNumber) {
        UserVO tempSocialUser = createTempSocialUser(email, name, request.platformType(), id, phoneNumber);
        redisUserRepository.save(tempSocialUser);

        return User.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .platformType(request.platformType())
                .platformId(id)
                .role(Role.GUEST)
                .build();
    }

    private static UserVO createTempSocialUser(String email, String name, PlatformType platformType, String id,
                                               String phoneNumber){
        return UserVO.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .platformType(platformType)
                .platformId(id)
                .build();
    }

    public void updateUniversityInfo(SignUpUserRequestDto requestDTO, Long universityId) {

        UserVO userVO = redisUserRepository.findByPlatformIdOrElseThrowException(requestDTO.platformId());

        userVO.updateUniversityId(universityId);
        userVO.updateEmail(requestDTO.email());
        userVO.updateAdmissionYear(requestDTO.admissionYear());

        redisUserRepository.save(userVO);
    }

    public User createSocialUser(UserVO userVO, String nickName) {
        University foundUniversity = universityRepository.findByIdOrElseThrowException(
                userVO.getUniversityId());

        User newUser = User.builder()
                .email(userVO.getEmail())
                .name(userVO.getName())
                .nickname(nickName)
                .phoneNumber(userVO.getPhoneNumber())
                .admissionYear(userVO.getAdmissionYear())
                .university(foundUniversity)
                .role(Role.USER)
                .platformType(userVO.getPlatformType())
                .platformId(userVO.getPlatformId())
                .build();

        return userRepository.saveAndFlush(newUser);

    }
}
