package synk.meeteam.domain.common.role;

import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.common.role.dto.RoleDto;
import synk.meeteam.domain.common.role.entity.Role;

public class RoleFixture {

    public static Role createRole(String roleName) {
        return new Role(roleName);
    }

    public static List<RoleDto> createRoleDtos() {
        List<RoleDto> roleDtos = new ArrayList<>();
        roleDtos.add(new RoleDto(1L, "웹 개발자"));
        return roleDtos;
    }
}
