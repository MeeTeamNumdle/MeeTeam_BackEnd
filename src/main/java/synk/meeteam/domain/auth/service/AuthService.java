package synk.meeteam.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.auth.api.dto.request.UserAuthRequestDTO;
import synk.meeteam.domain.auth.service.vo.UserSignUpVO;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.entity.enums.Role;
import synk.meeteam.domain.user.repository.UserRepository;
import synk.meeteam.security.jwt.utils.PasswordUtil;

@Service
@RequiredArgsConstructor
public abstract class AuthService {
    private final UserRepository userRepository;

    @Transactional
    public abstract UserSignUpVO saveUserOrLogin(String platformType, UserAuthRequestDTO request);

    protected User getUser(PlatformType platformType, String platformId) {
        return userRepository.findByPlatformIdAndPlatformType(platformId, platformType)
                .orElse(null);
    }

    protected User saveUser(UserAuthRequestDTO request, String email, String name, String id,
                            String phoneNumber) {
        User newUser = createSocialMember(email, name, request.platformType(), id, phoneNumber);
        return userRepository.saveAndFlush(newUser);
    }

    private static User createSocialMember(String email, String name, PlatformType platformType, String id,
                                             String phoneNumber) {
        String password = PasswordUtil.generateRandomPassword();

        return User.builder()
                .email(email)
                .name(name)
                .nickname("tmpNickName")
                .password(password)
                .phoneNumber(phoneNumber)
                .admissionYear(0)
                .platformType(platformType)
                .platformId(id)
                .role(Role.GUEST)
                .build();
    }
}
