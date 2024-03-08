package synk.meeteam.domain.recruitment.recruitment_role;

import static org.mockito.Mockito.doReturn;
import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.role.RoleFixture;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
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
        doReturn(recruitmentRole).when(recruitmentRoleRepository).save(recruitmentRole);

        // when
        RecruitmentRole savedRecruitmentRole = recruitmentRoleService.createRecruitmentRole(recruitmentRole);

        // then
        Assertions.assertThat(savedRecruitmentRole)
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
}
