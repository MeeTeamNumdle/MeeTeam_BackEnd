package synk.meeteam.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String getUserName(User user){
        return userRepository.findByPlatformIdOrElseThrowException(
                user.getPlatformId()).getName();
    }
}
