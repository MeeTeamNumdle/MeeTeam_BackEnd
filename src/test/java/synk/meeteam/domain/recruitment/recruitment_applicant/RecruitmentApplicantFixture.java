package synk.meeteam.domain.recruitment.recruitment_applicant;

import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitStatus;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.entity.User;

public class RecruitmentApplicantFixture {

    public static RecruitmentApplicant createRecruitmentApplicant(RecruitmentPost recruitmentPost, User applicant,
                                                                  Role role) {
        return RecruitmentApplicant.builder()
                .recruitmentPost(recruitmentPost)
                .applicant(applicant)
                .role(role)
                .comment("저 하고 싶어영")
                .recruitStatus(RecruitStatus.NONE)
                .build();
    }
}
