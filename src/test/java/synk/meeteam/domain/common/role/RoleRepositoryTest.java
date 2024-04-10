package synk.meeteam.domain.common.role;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.role.dto.RoleDto;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.exception.RoleException;
import synk.meeteam.domain.common.role.repository.RoleRepository;

@DataJpaTest
@ActiveProfiles("test")
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
        assertThat(role).isNotNull();
    }

    @Test
    void 특정역할조회_예외발생_유효하지않은Id입력값() {
        // given
        Long input = 0L;

        // when, then
        Assertions.assertThatThrownBy(() -> roleRepository.findByIdOrElseThrowException(input))
                .isInstanceOf(RoleException.class);
    }

    @Test
    void 역할목록조회_역할DTO반환_keyword는공백일때() {
        //given
        String keyword = "";
        long limit = 5;

        //when
        List<RoleDto> roles = roleRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(roles).extracting("name").containsExactlyInAnyOrder(
                "C,C++ 개발자", "DevOps / 시스템 관리자", "Node.js 개발자", "데이터 엔지니어", "머신러닝 엔지니어"
        );
        assertThat(roles.size()).isEqualTo(5);
    }

    @Test
    void 역할목록조회_역할DTO반환_keyword는웹일때() {
        //given
        String keyword = "웹";
        long limit = 5;

        //when
        List<RoleDto> roles = roleRepository.findAllByKeywordAndTopLimit(keyword, limit);

        //then
        assertThat(roles).extracting("name").contains("웹 개발자");
        assertThat(roles.size()).isEqualTo(1);
    }

}
