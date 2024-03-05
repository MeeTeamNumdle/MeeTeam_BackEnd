package synk.meeteam.domain.recruitment.recruitment_role;

import static org.mockito.Mockito.doReturn;
import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE;

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
        RecruitmentRole savedRecruitmentRole = recruitmentRoleService.createRecruitmentRoleV2(recruitmentRole);

        // then
        Assertions.assertThat(savedRecruitmentRole)
                .extracting("recruitmentPost", "role", "count")
                .containsExactly(recruitmentPost, role, 2L);
    }
}
