package synk.meeteam.domain.common.role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.role.dto.RoleDto;
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
        Role foundRole = roleService.findById(input);

        // then
        Assertions.assertThat(foundRole.getName()).isEqualTo(role.getName());
    }

    @Test
    void 특정역할조회_예외발생_유효하지않은Id입력값() {
        // given
        Long input = 2L;
        doThrow(RoleException.class).when(roleRepository).findByIdOrElseThrowException(input);

        // when, then
        Assertions.assertThatThrownBy(() -> roleService.findById(input)).isInstanceOf(RoleException.class);
    }

    @Test
    void 역할목록조회_역할목록DTO반환() {
        //given
        String keyword = "웹 개발자";
        long limit = 3;

        doReturn(RoleFixture.createRoleDtos())
                .when(roleRepository).findAllByKeywordAndTopLimit(keyword, limit);

        //when
        List<RoleDto> roleDtos = roleService.searchByKeyword(keyword, limit);

        //then
        assertThat(roleDtos).extracting("name").containsExactly("웹 개발자");
    }

}
