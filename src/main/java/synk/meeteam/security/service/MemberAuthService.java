package synk.meeteam.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.repository.UserRepository;
import synk.meeteam.global.util.Encryption;
import synk.meeteam.security.CustomAuthUser;

@Service
@RequiredArgsConstructor
public class MemberAuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String encryptedUserId) throws UsernameNotFoundException {
        User user = userRepository.findByIdOrElseThrow(Encryption.decryptLong(encryptedUserId));

        return new CustomAuthUser(user, user.getAuthority());
    }
}
