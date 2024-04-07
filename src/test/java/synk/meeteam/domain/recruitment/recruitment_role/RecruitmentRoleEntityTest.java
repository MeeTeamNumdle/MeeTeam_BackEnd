package synk.meeteam.domain.recruitment.recruitment_role;

import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.INVALID_USER;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import synk.meeteam.domain.common.role.RoleFixture;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantException;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;

public class RecruitmentRoleEntityTest {

    @Test
    void 구인인원증가_성공() {
        // given
        Long userId = 1L;
        long recruitedCount = 2L;
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목입니다");
        recruitmentPost.setCreatedBy(userId);
        Role role = RoleFixture.createRole("백엔드개발자");
        List<RecruitmentRole> recruitmentRoles = RecruitmentRoleFixture.createRecruitmentRoles(recruitmentPost, role);

        // when
        recruitmentRoles.stream().
                forEach(recruitmentRole -> recruitmentRole.incrementRecruitedCount(recruitedCount, userId));

        // then
        recruitmentRoles.stream().
                forEach(recruitmentRole -> Assertions.assertThat(recruitmentRole.getRecruitedCount())
                        .isEqualTo(recruitedCount));
    }

    @Test
    void 구인인원증가_예외발생_작성자가아닌경우() {
        // given
        Long userId = 1L;
        Long writerId = 2L;
        long recruitedCount = 2L;
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목입니다");
        recruitmentPost.setCreatedBy(writerId);
        Role role = RoleFixture.createRole("백엔드개발자");
        List<RecruitmentRole> recruitmentRoles = RecruitmentRoleFixture.createRecruitmentRoles(recruitmentPost, role);

        // when, then
        Assertions.assertThatThrownBy(() -> recruitmentRoles.stream().
                        forEach(recruitmentRole -> recruitmentRole.incrementRecruitedCount(recruitedCount, userId)))
                .isInstanceOf(RecruitmentApplicantException.class)
                .hasMessageContaining(INVALID_USER.message());
    }
}
