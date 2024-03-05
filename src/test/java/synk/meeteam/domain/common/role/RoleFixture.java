package synk.meeteam.domain.common.role;

import synk.meeteam.domain.common.role.entity.Role;

public class RoleFixture {

    public static Role createRole(String roleName) {
        return new Role(roleName);
    }
}
