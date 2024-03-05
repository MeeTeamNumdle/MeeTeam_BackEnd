package synk.meeteam.domain.recruitment.recruitment_role_skill;

import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;

public class RecruitmentRoleSkillFixture {

    public static RecruitmentRoleSkill createRecruitmentRoleSkill(RecruitmentRole recruitmentRole, Skill skill) {
        return RecruitmentRoleSkill.builder()
                .recruitmentRole(recruitmentRole)
                .skill(skill)
                .build();
    }
}
