package synk.meeteam.domain.recruitment.recruitment_role;

import static org.assertj.core.api.Assertions.assertThat;
import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.repository.RoleRepository;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;
import synk.meeteam.domain.recruitment.recruitment_role.dto.AvailableRecruitmentRoleDto;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.repository.RecruitmentRoleRepository;

@DataJpaTest
@ActiveProfiles("test")
public class RecruitmentRoleRepositoryTest {
    @Autowired
    private RecruitmentRoleRepository recruitmentRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    @Test
    void 구인역할저장_구인역할저장성공_정상입력경우() {
        // given
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(1L).orElse(null);
        Role role = roleRepository.findById(1L).orElse(null);
        RecruitmentRole recruitmentRole = RecruitmentRole.builder()
                .role(role)
                .recruitmentPost(recruitmentPost)
                .count(5L)
                .build();

        // when
        RecruitmentRole savedRecruitmentRole = recruitmentRoleRepository.saveAndFlush(recruitmentRole);
        RecruitmentRole foundRecruitmentRole = recruitmentRoleRepository.findById(savedRecruitmentRole.getId())
                .orElse(null);

        // then
        Assertions.assertThat(foundRecruitmentRole)
                .extracting("role", "recruitmentPost", "count")
                .containsExactly(role, recruitmentPost, 5L);
    }

    @Test
    void 구인역할저장_예외발생_RecruitmentPost가null인경우() {
        // given
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(1000L).orElse(null);
        Role role = roleRepository.findById(1L).orElse(null);
        RecruitmentRole recruitmentRole = RecruitmentRole.builder()
                .role(role)
                .recruitmentPost(recruitmentPost)
                .count(5L)
                .build();

        // when, then
        Assertions.assertThatThrownBy(() -> {
            recruitmentRoleRepository.saveAndFlush(recruitmentRole);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void 구인역할저장_예외발생_Role이null인경우() {
        // given
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(1L).orElse(null);
        Role role = roleRepository.findById(1000L).orElse(null);
        RecruitmentRole recruitmentRole = RecruitmentRole.builder()
                .role(role)
                .recruitmentPost(recruitmentPost)
                .count(5L)
                .build();

        // when, then
        Assertions.assertThatThrownBy(() -> {
            recruitmentRoleRepository.saveAndFlush(recruitmentRole);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
        // 이미 이 케이스는 RecruitmentPostRepositoryTest에서 확인하는 부분인데 여기서 하는게 의미가 있을까? 없을까?
    void 구인역할저장_예외발생_RecruitmentPost가db에저장되지않은경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Role role = roleRepository.findById(1L).orElse(null);
        RecruitmentRole recruitmentRole = RecruitmentRole.builder()
                .role(role)
                .recruitmentPost(recruitmentPost)
                .count(5L)
                .build();

        // when, then
        Assertions.assertThatThrownBy(() -> {
            recruitmentRoleRepository.saveAndFlush(recruitmentRole);
        }).isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    @Test
    @DisplayName("해당 메서드는 페치 조인을 사용한다.")
    void 구인역할조회_구인역할반환_구인글조회경우() {
        // given

        // when
        List<RecruitmentRole> recruitmentRoles = recruitmentRoleRepository.findByPostIdWithSkills(1L);

        // then
        for (RecruitmentRole role : recruitmentRoles) {
            assertThat(role.getRecruitmentPost().getId()).isEqualTo(1L);
            assertThat(role.getRole()).isNotNull();
        }
    }

    @Test
    void 신청가능역할조회_역할Dto반환_신청가능한구인역할이있는경우() {
        // given

        Long postId = 1L;

        // when
        List<AvailableRecruitmentRoleDto> availableRoleDtos = recruitmentRoleRepository.findAvailableRecruitmentRoleByRecruitmentId(
                postId);

        // then
        assertThat(availableRoleDtos.get(0).getName()).isEqualTo("소프트웨어 엔지니어");
        assertThat(availableRoleDtos.get(1).getName()).isEqualTo("웹 개발자");

    }

    @Test
    void 신청가능역할조회_빈역할Dto_신청가능한구인역할이없는경우() {
        // given
        Long postId = -1L;

        // when
        List<AvailableRecruitmentRoleDto> availableRoleDtos = recruitmentRoleRepository.findAvailableRecruitmentRoleByRecruitmentId(
                postId);

        // then
        assertThat(availableRoleDtos.size()).isEqualTo(0);

    }

    @Test
    void 구인역할조회_성공_id리스트로조회() {
        // given
        Long postId = 1L;
        List<Long> roleIds = List.of(1L, 2L);

        // when
        List<RecruitmentRole> recruitmentRoles = recruitmentRoleRepository.findAllByPostIdAndRoleIds(postId,
                roleIds);

        // then
        Assertions.assertThat(recruitmentRoles.get(0).getRole().getName()).isEqualTo("소프트웨어 엔지니어");
        Assertions.assertThat(recruitmentRoles.get(1).getRole().getName()).isEqualTo("웹 개발자");

    }

    @Test
    void 구인역할조회_성공_id리스트null인경우() {
        // given
        Long postId = 1L;
        List<Long> roleIds = null;

        // when
        List<RecruitmentRole> recruitmentRoles = recruitmentRoleRepository.findAllByPostIdAndRoleIds(postId,
                roleIds);

        // then
        Assertions.assertThat(recruitmentRoles.size()).isEqualTo(0);
    }
}
