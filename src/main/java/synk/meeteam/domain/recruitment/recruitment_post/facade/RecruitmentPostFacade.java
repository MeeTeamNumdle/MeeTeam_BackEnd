package synk.meeteam.domain.recruitment.recruitment_post.facade;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_role_skill.service.RecruitmentRoleSkillService;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;

@Service
@RequiredArgsConstructor
public class RecruitmentPostFacade {
    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;
    private final RecruitmentRoleSkillService recruitmentRoleSkillService;
    //   private final RecruitmentTagService recruitmentTagService;

    @Transactional
    public Long createRecruitmentPost(RecruitmentPost recruitmentPost, List<RecruitmentRole> recruitmentRoles,
                                      List<RecruitmentRoleSkill> recruitmentRoleSkills,
                                      List<RecruitmentTag> recruitmentTags) {

        RecruitmentPost newRecruitmentPost = recruitmentPostService.createRecruitmentPost(recruitmentPost);

        recruitmentRoles.stream()
                .forEach(recruitmentRole -> recruitmentRoleService.createRecruitmentRoleV2(recruitmentRole));

        recruitmentRoleSkills.forEach(
                recruitmentRoleSkill -> recruitmentRoleSkillService.createRecruitmentRoleSkill(recruitmentRoleSkill));

        //    recruitmentTags.stream().forEach(recruitmentTag -> recruitmentTagService.createRecruitmentTag(recruitmentTag));

        return newRecruitmentPost.getId();
    }

}
