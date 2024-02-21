package synk.meeteam.global.config;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.CustomAuthUser;

@RequiredArgsConstructor
@Component
public class UserAuditorAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            CustomAuthUser customAuthUser = (CustomAuthUser) authentication.getPrincipal();
            user = customAuthUser.getUser();
        }
        if (user == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(user.getId());
    }
}
