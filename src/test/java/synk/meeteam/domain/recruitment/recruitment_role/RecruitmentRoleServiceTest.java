package synk.meeteam.domain.recruitment.recruitment_role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.role.RoleFixture;
import synk.meeteam.domain.common.role.dto.RoleDto;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_role.dto.AvailableRecruitmentRoleDto;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.repository.RecruitmentRoleRepository;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;

@ExtendWith(MockitoExtension.class)
public class RecruitmentRoleServiceTest {
    @InjectMocks
    private RecruitmentRoleService recruitmentRoleService;

    @Mock
    private RecruitmentRoleRepository recruitmentRoleRepository;

    @Test
    void 구인역할생성_구인역할생성성공_정상입력경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Role role = RoleFixture.createRole("백엔드개발자");
        RecruitmentRole recruitmentRole = RecruitmentRoleFixture.createRecruitmentRoleFixture(recruitmentPost,
                role, 2L);
        List<RecruitmentRole> recruitmentRoles = new ArrayList<>();
        recruitmentRoles.add(recruitmentRole);
        doReturn(recruitmentRoles).when(recruitmentRoleRepository).saveAll(any());

        // when
        List<RecruitmentRole> savedRecruitmentRoles = recruitmentRoleService.createRecruitmentRoles(recruitmentRoles);

        // then
        Assertions.assertThat(savedRecruitmentRoles.get(0))
                .extracting("recruitmentPost", "role", "count")
                .containsExactly(recruitmentPost, role, 2L);
    }

    @Test
    void 구인역할조회_구인역할반환_구인글조회경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Role role = RoleFixture.createRole("백엔드개발자");
        doReturn(RecruitmentRoleFixture.createRecruitmentRoles(recruitmentPost, role)).when(recruitmentRoleRepository)
                .findByPostIdWithSkills(1L);

        // when
        List<RecruitmentRole> recruitmentRoles = recruitmentRoleService.findByRecruitmentPostId(1L);

        // then
        Assertions.assertThat(recruitmentRoles.get(0))
                .extracting("recruitmentPost", "count")
                .containsExactly(recruitmentPost, 3L);
        Assertions.assertThat(recruitmentRoles.get(1))
                .extracting("recruitmentPost", "count")
                .containsExactly(recruitmentPost, 2L);
        Assertions.assertThat(recruitmentRoles.get(2))
                .extracting("recruitmentPost", "count")
                .containsExactly(recruitmentPost, 1L);

    }

    @Test
    void 전체신청자수더하기1_전체신청자수1증가() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Role role = RoleFixture.createRole("백엔드개발자");
        RecruitmentRole recruitmentRole = RecruitmentRoleFixture.createRecruitmentRoleFixture(recruitmentPost,
                role, 2L);
        long cur = recruitmentRole.getApplicantCount();

        // when
        recruitmentRoleService.incrementApplicantCount(recruitmentRole);

        // then
        Assertions.assertThat(recruitmentRole.getApplicantCount()).isEqualTo(cur + 1);
    }

    @Test
    void 신청가능역할조회_역할Dto반환_신청가능한구인역할이있는경우() {
        // given
        Long postId = 1L;
        doReturn(RoleFixture.createRoleDtos())
                .when(recruitmentRoleRepository).findAvailableRecruitmentRoleByRecruitmentId(postId);

        // when
        List<AvailableRecruitmentRoleDto> availableRecruitmentRoleDtos = recruitmentRoleService.findAvailableRecruitmentRole(
                postId);

        // then
        assertThat(availableRecruitmentRoleDtos).extracting("name").containsExactly("웹 개발자");

    }

    @Test
    void 신청가능역할조회_역할Dto반환_신청가능한구인역할이없는경우() {
        // given
        Long postId = 1L;
        List<RoleDto> roleDtos = new ArrayList<>();
        doReturn(roleDtos)
                .when(recruitmentRoleRepository).findAvailableRecruitmentRoleByRecruitmentId(postId);

        // when
        List<AvailableRecruitmentRoleDto> availableRecruitmentRoleDtos = recruitmentRoleService.findAvailableRecruitmentRole(
                postId);

        // then
        assertThat(availableRecruitmentRoleDtos.size()).isEqualTo(0);

    }
}
