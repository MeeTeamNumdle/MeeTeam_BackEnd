package synk.meeteam.domain.user.user.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {

    GUEST("ROLE_GUEST"), USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String key;
}
