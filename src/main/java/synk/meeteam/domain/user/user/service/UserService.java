package synk.meeteam.domain.user.user.service;

import static synk.meeteam.domain.user.user.exception.UserExceptionType.DUPLICATE_NICKNAME;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.repository.RoleRepository;
import synk.meeteam.domain.user.user.dto.command.UpdateProfileCommand;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.exception.UserException;
import synk.meeteam.domain.user.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public boolean checkAvailableNickname(String nickname) {
        User foundUser = userRepository.findByNickname(nickname).orElse(null);
        return foundUser == null;
    }

    @Transactional
    public void updateNickname(User user, String newNickname) {
        if (user.isNotEqualNickname(newNickname)) {
            if (checkAvailableNickname(newNickname)) {
                user.updateNickname(newNickname);
            } else {
                throw new UserException(DUPLICATE_NICKNAME);
            }
        }
    }

    @Transactional
    public void updateProfile(User user, UpdateProfileCommand command) {

        Role interestRole = roleRepository.findByIdOrElseThrowException(command.interest_id());
        user.updateProfile(
                command.name(),
                command.pictureUrl(),
                command.subEmail(),
                command.isPublicSubEmail(),
                command.isPublicSchoolEmail(),
                command.isSchoolMain(),
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
}
