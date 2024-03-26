package synk.meeteam.domain.recruitment.recruitment_post.facade;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.bookmark.service.BookmarkService;
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
import synk.meeteam.domain.user.user.entity.User;


@Service
@RequiredArgsConstructor
public class RecruitmentPostFacade {
    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;
    private final RecruitmentRoleSkillService recruitmentRoleSkillService;
    private final RecruitmentTagService recruitmentTagService;
    private final RecruitmentApplicantService recruitmentApplicantService;
    private final BookmarkService bookmarkService;


    @Transactional
    public Long createRecruitmentPost(RecruitmentPost recruitmentPost, List<RecruitmentRole> recruitmentRoles,
                                      List<RecruitmentRoleSkill> recruitmentRoleSkills,
                                      List<RecruitmentTag> recruitmentTags) {

        RecruitmentPost newRecruitmentPost = recruitmentPostService.writeRecruitmentPost(recruitmentPost);

        recruitmentRoles.forEach(recruitmentRoleService::createRecruitmentRole);

        recruitmentRoleSkills.forEach(recruitmentRoleSkillService::createRecruitmentRoleSkill);

        recruitmentTags.forEach(recruitmentTagService::createRecruitmentTag);

        return newRecruitmentPost.getId();
    }

    @Transactional
    public void applyRecruitment(RecruitmentRole recruitmentRole, RecruitmentApplicant recruitmentApplicant) {
        recruitmentApplicantService.registerRecruitmentApplicant(recruitmentApplicant);

        recruitmentPostService.incrementApplicantCount(recruitmentApplicant.getRecruitmentPost());

        recruitmentRoleService.addApplicantCount(recruitmentRole);
    }

    @Transactional
    public void bookmarkRecruitmentPost(RecruitmentPost recruitmentPost, User user) {
        bookmarkService.bookmarkRecruitmentPost(recruitmentPost, user);
        recruitmentPostService.incrementBookmarkCount(recruitmentPost);
    }

}
