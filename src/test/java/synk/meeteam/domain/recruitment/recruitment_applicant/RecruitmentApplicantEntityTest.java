package synk.meeteam.domain.recruitment.recruitment_applicant;

import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.ALREADY_PROCESSED_APPLICANT;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import synk.meeteam.domain.common.role.RoleFixture;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitStatus;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantException;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.UserFixture;
import synk.meeteam.domain.user.user.entity.User;

public class RecruitmentApplicantEntityTest {

    @Test
    void 승인가능여부검증_성공() {
        // given
        User user = UserFixture.createUserFixture();
        Role role = RoleFixture.createRole("백엔드개발자");
        Long userId = 1L;
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목입니다");
        recruitmentPost.setCreatedBy(userId);

        RecruitmentApplicant recruitmentApplicant = RecruitmentApplicant.builder()
                .recruitmentPost(recruitmentPost)
                .applicant(user)
                .role(role)
                .comment("나를 뽑아줘")
                .recruitStatus(RecruitStatus.NONE)
                .build();

        // when, then
        recruitmentApplicant.validateCanApprove(userId);
    }

    @Test
    void 승인가능여부검증_예외발생_NONE이아닌경우() {
        // given
        User user = UserFixture.createUserFixture();
        Role role = RoleFixture.createRole("백엔드개발자");
        Long userId = 1L;
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목입니다");
        recruitmentPost.setCreatedBy(userId);

        RecruitmentApplicant recruitmentApplicant = RecruitmentApplicant.builder()
                .recruitmentPost(recruitmentPost)
                .applicant(user)
                .role(role)
                .comment("나를 뽑아줘")
                .recruitStatus(RecruitStatus.APPROVED)
                .build();

        // when, then
        Assertions.assertThatThrownBy(() -> recruitmentApplicant.validateCanApprove(userId))
                .isInstanceOf(RecruitmentApplicantException.class)
                .hasMessageContaining(ALREADY_PROCESSED_APPLICANT.message());
    }
}
