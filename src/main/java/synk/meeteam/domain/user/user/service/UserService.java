package synk.meeteam.domain.user.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean checkDuplicateNickname(String nickname) {
        User foundUser = userRepository.findByNickname(nickname).orElse(null);
        if (foundUser != null) {
            return false;
        }
        return true;
    }
}
