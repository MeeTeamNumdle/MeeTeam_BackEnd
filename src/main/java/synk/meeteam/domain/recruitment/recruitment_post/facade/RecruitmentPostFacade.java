package synk.meeteam.domain.recruitment.recruitment_post.facade;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.service.RecruitmentApplicantService;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_role_skill.service.RecruitmentRoleSkillService;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;
import synk.meeteam.domain.recruitment.recruitment_tag.service.RecruitmentTagService;


@Service
@RequiredArgsConstructor
public class RecruitmentPostFacade {
    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;
    private final RecruitmentRoleSkillService recruitmentRoleSkillService;
    private final RecruitmentTagService recruitmentTagService;
    private final RecruitmentApplicantService recruitmentApplicantService;


    @Transactional
    public Long createRecruitmentPost(RecruitmentPost recruitmentPost, List<RecruitmentRole> recruitmentRoles,
                                      List<RecruitmentRoleSkill> recruitmentRoleSkills,
                                      List<RecruitmentTag> recruitmentTags) {
        RecruitmentPost newRecruitmentPost = recruitmentPostService.writeRecruitmentPost(recruitmentPost);
        recruitmentRoleService.createRecruitmentRoles(recruitmentRoles);
        recruitmentRoleSkillService.createRecruitmentRoleSkills(recruitmentRoleSkills);
        recruitmentTagService.createRecruitmentTags(recruitmentTags);

        return newRecruitmentPost.getId();
    }

    @Transactional
    public void modifyRecruitmentPost(RecruitmentPost dstRecruitmentPost, RecruitmentPost srcRecruitmentPost,
                                      List<RecruitmentRole> recruitmentRoles,
                                      List<RecruitmentRoleSkill> recruitmentRoleSkills,
                                      List<RecruitmentTag> recruitmentTags) {

        recruitmentPostService.modifyRecruitmentPost(dstRecruitmentPost, srcRecruitmentPost);

        // cascade 설정을 하여 recruitmentRoleService에서 Role과 Skills를 한 번에 삭제한다.
        recruitmentRoleService.modifyRecruitmentRoleAndSkills(recruitmentRoles, recruitmentRoleSkills,
                dstRecruitmentPost.getId());

        recruitmentTagService.modifyRecruitmentTag(recruitmentTags, dstRecruitmentPost.getId());
    }

    @Transactional
    public void applyRecruitment(RecruitmentRole recruitmentRole, RecruitmentApplicant recruitmentApplicant) {
        recruitmentApplicantService.registerRecruitmentApplicant(recruitmentApplicant);

        recruitmentPostService.incrementApplicantCount(recruitmentApplicant.getRecruitmentPost());

        recruitmentRoleService.addApplicantCount(recruitmentRole);
    }
}
