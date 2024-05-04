package synk.meeteam.domain.recruitment.recruitment_post.facade;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.course.entity.Course;
import synk.meeteam.domain.common.course.entity.Professor;
import synk.meeteam.domain.common.course.service.CourseService;
import synk.meeteam.domain.common.course.service.ProfessorService;
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
import synk.meeteam.infra.mail.MailService;


@Service
@RequiredArgsConstructor
public class RecruitmentPostFacade {
    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;
    private final RecruitmentRoleSkillService recruitmentRoleSkillService;
    private final RecruitmentTagService recruitmentTagService;
    private final RecruitmentApplicantService recruitmentApplicantService;
    private final BookmarkService bookmarkService;

    private final MailService mailService;

    @Transactional
    public Long createRecruitmentPost(RecruitmentPost recruitmentPost, List<RecruitmentRole> recruitmentRoles,
                                      List<RecruitmentRoleSkill> recruitmentRoleSkills,
                                      List<RecruitmentTag> recruitmentTags, Course course, Professor professor) {
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
    public void applyRecruitment(RecruitmentRole recruitmentRole, RecruitmentApplicant recruitmentApplicant,
                                 User writer) {
        recruitmentApplicantService.registerRecruitmentApplicant(recruitmentApplicant);

        recruitmentPostService.incrementApplicantCount(recruitmentApplicant.getRecruitmentPost());

        recruitmentRoleService.incrementApplicantCount(recruitmentRole);

        mailService.sendApplicationNotificationMail(recruitmentApplicant.getRecruitmentPost().getId(),
                recruitmentApplicant, writer);
    }

    @Transactional
    public void cancelApplyRecruitment(Long postId, User user) {
        RecruitmentPost recruitmentPost = recruitmentPostService.getRecruitmentPost(postId);
        RecruitmentApplicant recruitmentApplicant = recruitmentApplicantService.getApplicant(recruitmentPost, user);
        RecruitmentRole recruitmentRole = recruitmentRoleService.findApplyRecruitmentRole(recruitmentPost,
                recruitmentApplicant.getRole());

        recruitmentApplicantService.cancelRegisterRecruitmentApplicant(recruitmentApplicant);
        recruitmentPostService.decrementApplicantCount(recruitmentPost);
        recruitmentRoleService.decrementApplicantCount(recruitmentRole);
    }

    @Transactional
    public void bookmarkRecruitmentPost(Long postId, User user) {
        RecruitmentPost recruitmentPost = recruitmentPostService.getRecruitmentPost(postId);

        bookmarkService.bookmarkRecruitmentPost(recruitmentPost, user);
        recruitmentPostService.incrementBookmarkCount(recruitmentPost);
    }

    @Transactional
    public void cancelBookmarkRecruitmentPost(Long postId, User user) {
        RecruitmentPost recruitmentPost = recruitmentPostService.getRecruitmentPost(postId);

        bookmarkService.cancelBookmarkRecruitmentPost(recruitmentPost, user);
        recruitmentPostService.decrementBookmarkCount(recruitmentPost);
    }

}
