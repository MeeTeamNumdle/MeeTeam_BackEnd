package synk.meeteam.domain.recruitment.recruitment_role.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_role.dto.AvailableRecruitmentRoleDto;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.repository.RecruitmentRoleRepository;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_role_skill.repository.RecruitmentRoleSkillRepository;

@Service
@RequiredArgsConstructor
public class RecruitmentRoleService {
    private final RecruitmentRoleRepository recruitmentRoleRepository;
    private final RecruitmentRoleSkillRepository recruitmentRoleSkillRepository;

    @Transactional
    public List<RecruitmentRole> createRecruitmentRoles(List<RecruitmentRole> recruitmentRoles) {
        return recruitmentRoleRepository.saveAll(recruitmentRoles);
    }

    public List<RecruitmentRole> findByRecruitmentPostId(Long recruitmentPostId) {
        return recruitmentRoleRepository.findByPostIdWithSkills(recruitmentPostId);
    }

    @Transactional(readOnly = true)
    public RecruitmentRole findAppliableRecruitmentRole(Long recruitmentRoleId) {
        return recruitmentRoleRepository.findByIdWithRecruitmentRoleAndRoleOrElseThrow(recruitmentRoleId);
    }

    @Transactional
    public void addApplicantCount(RecruitmentRole recruitmentRole) {
        recruitmentRole.addApplicantCount();
    }

    @Transactional(readOnly = true)
    public List<AvailableRecruitmentRoleDto> findAvailableRecruitmentRole(Long postId) {
        return recruitmentRoleRepository.findAvailableRecruitmentRoleByRecruitmentId(
                postId);
    }

    @Transactional
    public void modifyRecruitmentRoleAndSkills(List<RecruitmentRole> recruitmentRoles,
                                               List<RecruitmentRoleSkill> recruitmentRoleSkills, Long postId) {
        recruitmentRoleRepository.deleteAllByRecruitmentPostId(postId);

        recruitmentRoleRepository.saveAll(recruitmentRoles);
        recruitmentRoleSkillRepository.saveAll(recruitmentRoleSkills);
    }

    @Transactional(readOnly = true)
    public List<RecruitmentRole> findApplyStatusRecruitmentRole(Long postId) {
        return recruitmentRoleRepository.findAllByPostIdWithRecruitmentRoleAndRole(postId);
    }
}
