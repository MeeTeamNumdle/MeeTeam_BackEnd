package synk.meeteam.domain.user.user.service;

import synk.meeteam.domain.user.user.dto.command.UpdateInfoCommand;
import synk.meeteam.domain.user.user.entity.User;

public interface UserService {
    boolean checkAvailableNickname(String nickname);

    void changeOrKeepNickname(User user, String newNickname);

    void changeUserInfo(User user, UpdateInfoCommand command);

    User findById(Long userId);

    User findByEncryptedId(String encryptedId);
}
