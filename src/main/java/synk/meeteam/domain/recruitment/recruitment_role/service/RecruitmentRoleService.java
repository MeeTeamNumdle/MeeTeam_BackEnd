package synk.meeteam.domain.recruitment.recruitment_role.service;

import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.INVALID_USER;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantException;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
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

    @Transactional(readOnly = true)
    public RecruitmentRole findApplyRecruitmentRole(RecruitmentPost recruitmentPost, Role role) {
        return recruitmentRoleRepository.findByRecruitmentPostAndRoleOrElseThrow(recruitmentPost, role);
    }

    @Transactional
    public void incrementApplicantCount(RecruitmentRole recruitmentRole) {
        recruitmentRole.incrementApplicantCount();
    }

    @Transactional
    public void decrementApplicantCount(RecruitmentRole recruitmentRole) {
        recruitmentRole.decrementApplicantCount();
    }

    @Transactional(readOnly = true)
    public List<AvailableRecruitmentRoleDto> findAvailableRecruitmentRole(Long postId) {
        return recruitmentRoleRepository.findAvailableRecruitmentRoleByRecruitmentId(postId);
    }

    @Transactional
    public void modifyRecruitmentRoleAndSkills(List<RecruitmentRole> recruitmentRoles,
                                               List<RecruitmentRoleSkill> recruitmentRoleSkills, Long postId) {
        recruitmentRoleRepository.deleteAllByRecruitmentPostId(postId);

        recruitmentRoleRepository.saveAll(recruitmentRoles);
        recruitmentRoleSkillRepository.saveAll(recruitmentRoleSkills);
    }

    @Transactional(readOnly = true)
    public List<RecruitmentRole> findApplyStatusRecruitmentRole(Long postId, Long userId, Long writerId) {
        validateIsWriter(userId, writerId);

        return recruitmentRoleRepository.findAllByPostIdWithRecruitmentRoleAndRole(postId);
    }

    @Transactional
    public void incrementRecruitedCount(Long postId, Long userId, List<Long> roleIds, Map<Long, Long> recruitedCounts) {
        List<RecruitmentRole> recruitmentRoles = recruitmentRoleRepository.findAllByPostIdAndRoleIds(postId, roleIds);

        recruitmentRoles.stream()
                .forEach(recruitmentRole -> {
                    long recruitedCount = recruitedCounts.get(recruitmentRole.getRole().getId());
                    recruitmentRole.incrementRecruitedCount(recruitedCount, userId);
                });
    }

    private void validateIsWriter(Long userId, Long writerId){

        if(!userId.equals(writerId)){
            throw new RecruitmentApplicantException(INVALID_USER);
        }
    }
}
