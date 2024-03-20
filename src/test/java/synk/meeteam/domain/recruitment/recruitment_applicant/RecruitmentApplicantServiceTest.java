package synk.meeteam.domain.recruitment.recruitment_applicant;

import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.role.RoleFixture;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.repository.RecruitmentApplicantRepository;
import synk.meeteam.domain.recruitment.recruitment_applicant.service.RecruitmentApplicantService;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.UserFixture;
import synk.meeteam.domain.user.user.entity.User;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RecruitmentApplicantServiceTest {
    @InjectMocks
    private RecruitmentApplicantService recruitmentApplicantService;

    @Mock
    private RecruitmentApplicantRepository recruitmentApplicantRepository;

    @Test
    void 신청자저장_신청자정보반환_정상경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목");
        User user = UserFixture.createUser();
        Role role = RoleFixture.createRole("백엔드개발자");
        RecruitmentApplicant recruitmentApplicant = RecruitmentApplicantFixture.createRecruitmentApplicant(
                recruitmentPost, user, role);

        doReturn(recruitmentApplicant)
                .when(recruitmentApplicantRepository).save(recruitmentApplicant);

        // when
        recruitmentApplicantService.registerRecruitmentApplicant(recruitmentApplicant);

        // then
    }
}
