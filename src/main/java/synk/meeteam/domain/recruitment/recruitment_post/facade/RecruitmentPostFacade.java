package synk.meeteam.domain.recruitment.recruitment_post.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_post.dto.RecruitmentPostMapper;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;

@Service
@RequiredArgsConstructor
public class RecruitmentPostFacade {
    private final RecruitmentPostService recruitmentPostService;
    //    private final RecruitmentRoleService recruitmentRole;
//    private final RecruitmentRoleSkillService recruitmentRoleSkill;
//    private final RecruitmentTagService recruitmentTagService;
    private final RecruitmentPostMapper recruitmentPostMapper;

    @Transactional
    public Long createRecruitmentPost(RecruitmentPost recruitmentPost, RecruitmentRole recruitmentRole,
                                      RecruitmentRoleSkill recruitmentRoleSkill, RecruitmentTag recruitmentTag) {
        // recruitment_post 생성
        RecruitmentPost newRecruitmentPost = recruitmentPostService.createRecruitmentPost(recruitmentPost);

        // recruitment_role 생성

        // recruitment_role_skill 생성

        // recruitment_tag 생성

        return newRecruitmentPost.getId();
    }

}
