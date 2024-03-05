package synk.meeteam.domain.recruitment.recruitment_role;

import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE;

import org.assertj.core.api.Assertions;
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
}
