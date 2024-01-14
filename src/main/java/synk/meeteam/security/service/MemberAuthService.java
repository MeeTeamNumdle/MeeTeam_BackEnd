package synk.meeteam.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.repository.UserRepository;
import synk.meeteam.security.CustomAuthUser;

@Service
@RequiredArgsConstructor
public class MemberAuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String platformId) throws UsernameNotFoundException {
        User user = userRepository.findByPlatformIdOrElseThrowException(platformId);

        return new CustomAuthUser(user, user.getRole());
    }
}
