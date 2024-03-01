package synk.meeteam.domain.recruitment.recruitment_role_skill.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_role_skill.repository.RecruitmentRoleSkillRepository;

@Service
@RequiredArgsConstructor
public class RecruitmentRoleSkillService {
    private final RecruitmentRoleSkillRepository recruitmentRoleSkillRepository;

    @Transactional
    public RecruitmentRoleSkill createRecruitmentRoleSkill(RecruitmentRoleSkill recruitmentRoleSkill) {
        return recruitmentRoleSkillRepository.save(recruitmentRoleSkill);
    }
}
