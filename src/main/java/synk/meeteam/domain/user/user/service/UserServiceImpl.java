package synk.meeteam.domain.user.user.service;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.NOT_FOUND_USER;
import static synk.meeteam.domain.user.user.exception.UserExceptionType.DUPLICATE_NICKNAME;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.repository.RoleRepository;
import synk.meeteam.domain.user.user.dto.command.UpdateInfoCommand;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.exception.UserException;
import synk.meeteam.domain.user.user.repository.UserRepository;
import synk.meeteam.global.util.Encryption;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public boolean checkAvailableNickname(String nickname) {
        User foundUser = userRepository.findByNickname(nickname).orElse(null);
        return foundUser == null;
    }

    @Transactional
    public void changeOrKeepNickname(User user, String newNickname) {
        if (user.isNotEqualNickname(newNickname)) {
            if (checkAvailableNickname(newNickname)) {
                user.updateNickname(newNickname);
            } else {
                throw new UserException(DUPLICATE_NICKNAME);
            }
        }
    }

    @Transactional
    public void changeUserInfo(User user, UpdateInfoCommand command) {

        Role interestRole = roleRepository.findByIdOrElseThrowException(command.interestId());
        user.updateProfile(
                command.isPublicName(),
                command.pictureFileName(),
                command.subEmail(),
                command.isPublicSubEmail(),
                command.isPublicUniversityEmail(),
                command.isUniversityMainEmail(),
                command.phoneNumber(),
                command.isPublicPhone(),
                command.oneLineIntroduction(),
                command.mainIntroduction(),
                command.gpa(),
                command.maxGpa(),
                interestRole
        );
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findByIdOrElseThrow(userId);
    }

    public User findByEncryptedId(String encryptedId) {
        Long userId = Encryption.decryptLong(encryptedId);
        if (userId == null) {
            throw new UserException(NOT_FOUND_USER);
        }
        return userRepository.findByIdFetchRole(userId);
    }
}
