package synk.meeteam.domain.common.role;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.exception.RoleException;
import synk.meeteam.domain.common.role.repository.RoleRepository;

@DataJpaTest
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void 특정역할조회_특정역할반환_유효한Id입력값() {
        // given
        Long input = 1L;

        // when
        Role role = roleRepository.findByIdOrElseThrowException(input);

        // then
        Assertions.assertThat(role).isNotNull();
    }

    @Test
    void 특정역할조회_예외발생_유효하지않은Id입력값() {
        // given
        Long input = 14L;

        // when, then
        Assertions.assertThatThrownBy(() -> roleRepository.findByIdOrElseThrowException(input))
                .isInstanceOf(RoleException.class);
    }
}
