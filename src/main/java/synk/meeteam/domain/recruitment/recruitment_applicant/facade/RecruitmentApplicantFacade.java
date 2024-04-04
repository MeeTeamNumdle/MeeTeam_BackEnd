package synk.meeteam.domain.recruitment.recruitment_applicant.facade;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.service.RecruitmentApplicantService;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;

@Service
@RequiredArgsConstructor
public class RecruitmentApplicantFacade {

    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;
    private final RecruitmentApplicantService recruitmentApplicantService;

    @Transactional
    public void approveApplicant(Long postId, Long userId, List<Long> applicantIds) {

        List<RecruitmentApplicant> applicants = recruitmentApplicantService.getAllApplicants(applicantIds);
        recruitmentApplicantService.approveApplicants(applicants, applicantIds, userId);

        List<Long> roleIds = recruitmentApplicantService.getRoleIds(applicants);
        Map<Long, Long> recruitedCounts = recruitmentApplicantService.getRecruitedCounts(applicants);

        recruitmentPostService.incrementResponseCount(postId, userId, applicantIds.size());
        recruitmentRoleService.incrementRecruitedCount(postId, roleIds, recruitedCounts);
    }
}
