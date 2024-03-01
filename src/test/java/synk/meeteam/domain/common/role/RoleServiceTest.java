package synk.meeteam.domain.common.role;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.exception.RoleException;
import synk.meeteam.domain.common.role.repository.RoleRepository;
import synk.meeteam.domain.common.role.service.RoleService;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void 특정역할조회_특정역할반환_유효한Id입력값() {
        // given
        Long input = 2L;
        Role role = RoleFixture.createRole("백엔드개발자");
        doReturn(role).when(roleRepository).findByIdOrElseThrowException(input);

        // when
        Role foundRole = roleService.findRoleById(input);

        // then
        Assertions.assertThat(foundRole.getName()).isEqualTo(role.getName());
    }

    @Test
    void 특정역할조회_예외발생_유효하지않은Id입력값() {
        // given
        Long input = 2L;
        doThrow(RoleException.class).when(roleRepository).findByIdOrElseThrowException(input);

        // when, then
        Assertions.assertThatThrownBy(() -> roleService.findRoleById(input)).isInstanceOf(RoleException.class);
    }
}
