package synk.meeteam.domain.recruitment.recruitment_applicant.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.service.RecruitmentApplicantService;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
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

        recruitmentApplicantService.approveApplicants(applicantIds, applicants, userId);
        recruitmentPostService.incrementResponseCount(postId, userId, applicantIds.size());

        Map<Long, Long> recruitedRoles = new HashMap<>();
        List<Long> roleIds = applicants.stream()
                .map(applicant -> {
                    recruitedRoles.putIfAbsent(applicant.getRole().getId(), 0L);

                    recruitedRoles.put(applicant.getRole().getId(),
                            recruitedRoles.get(applicant.getRole().getId()) + 1);
                    return applicant.getRole().getId();
                }).toList();

        List<RecruitmentRole> recruitmentRoles = recruitmentRoleService.findAllByRecruitmentAndRoles(postId,
                roleIds);

        recruitmentRoles.stream()
                .forEach(recruitmentRole -> {
                    long recruitedCount = recruitedRoles.get(recruitmentRole.getRole().getId());
                    recruitmentRole.incrementRecruitedCount(recruitedCount);
                });
    }
}
