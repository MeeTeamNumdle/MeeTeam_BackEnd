package synk.meeteam.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.auth.dto.request.AuthUserRequestDto;
import synk.meeteam.domain.auth.dto.request.VerifyEmailRequestDto;
import synk.meeteam.domain.auth.service.vo.AuthUserVo;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.department.repository.DepartmentRepository;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.UserVO;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.user.repository.UserRepository;
import synk.meeteam.infra.redis.repository.RedisUserRepository;

@Service
@RequiredArgsConstructor
public abstract class AuthService {
    private final UserRepository userRepository;
    private final RedisUserRepository redisUserRepository;
    private final UniversityRepository universityRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public abstract AuthUserVo saveUserOrLogin(String platformType, AuthUserRequestDto request);

    protected User getUser(PlatformType platformType, String platformId) {
        return userRepository.findByPlatformIdAndPlatformType(platformId, platformType)
                .orElse(null);
    }

    protected User saveTempUser(AuthUserRequestDto request, String email, String name, String id,
                                String phoneNumber, String pictureUrl) {
        UserVO tempSocialUser = createTempSocialUser(email, name, request.platformType(), id, phoneNumber, pictureUrl);
        redisUserRepository.save(tempSocialUser);

        return User.builder()
                .schoolEmail(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .platformType(request.platformType())
                .platformId(id)
                .authority(Authority.GUEST)
                .pictureUrl(pictureUrl)
                .build();
    }

    private static UserVO createTempSocialUser(String email, String name, PlatformType platformType, String id,
                                               String phoneNumber, String pictureUrl) {
        return UserVO.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .platformType(platformType)
                .platformId(id)
                .pictureUrl(pictureUrl)
                .build();
    }

    @Transactional
    public void updateUniversityInfo(VerifyEmailRequestDto requestDTO, String email) {
        UserVO userVO = redisUserRepository.findByPlatformIdOrElseThrowException(requestDTO.platformId());
        userVO.updateUniversityInfo(requestDTO.universityId(), requestDTO.departmentId(), requestDTO.year(),
                email);

        redisUserRepository.save(userVO);
    }

    @Transactional
    public User createSocialUser(UserVO userVO, String nickName) {
        University foundUniversity = universityRepository.findByIdOrElseThrowException(
                userVO.getUniversityId());
        Department foundDepartment = departmentRepository.findByIdOrElseThrowException(
                userVO.getDepartmentId());

        User newUser = User.builder()
                .schoolEmail(userVO.getEmail())
                .name(userVO.getName())
                .nickname(nickName)
                .phoneNumber(userVO.getPhoneNumber())
                .admissionYear(userVO.getAdmissionYear())
                .university(foundUniversity)
                .department(foundDepartment)
                .authority(Authority.USER)
                .platformType(userVO.getPlatformType())
                .platformId(userVO.getPlatformId())
                .build();

        return userRepository.saveAndFlush(newUser);

    }
}
