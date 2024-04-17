package synk.meeteam.domain.recruitment.recruitment_applicant.facade;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.service.RecruitmentApplicantService;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.infra.mail.MailService;

@Service
@RequiredArgsConstructor
public class RecruitmentApplicantFacade {

    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;
    private final RecruitmentApplicantService recruitmentApplicantService;
    private final UserService userService;

    private final MailService mailService;

    @Transactional
    public void approveApplicant(Long postId, Long userId, List<Long> applicantIds) {

        List<RecruitmentApplicant> applicants = recruitmentApplicantService.getAllApplicants(applicantIds);
        recruitmentApplicantService.approveApplicants(applicants, applicantIds, userId);

        List<Long> roleIds = recruitmentApplicantService.getRoleIds(applicants);
        Map<Long, Long> recruitedCounts = recruitmentApplicantService.getRecruitedCounts(applicants);

        recruitmentPostService.incrementResponseCount(postId, userId, applicantIds.size());
        recruitmentRoleService.incrementRecruitedCount(postId, userId, roleIds, recruitedCounts);

        RecruitmentPost recruitmentPost = recruitmentPostService.getRecruitmentPost(postId);
        User user = userService.findById(recruitmentPost.getCreatedBy());

        applicants.stream().forEach(
                applicant -> mailService.sendApproveMail(postId, applicant, user.getName())
        );
    }

    @Transactional
    public void rejectApplicants(Long postId, Long userId, List<Long> applicantIds) {

        List<RecruitmentApplicant> applicants = recruitmentApplicantService.getAllApplicants(applicantIds);
        recruitmentApplicantService.rejectApplicants(applicants, applicantIds, userId);

        recruitmentPostService.incrementResponseCount(postId, userId, applicantIds.size());
    }
}
