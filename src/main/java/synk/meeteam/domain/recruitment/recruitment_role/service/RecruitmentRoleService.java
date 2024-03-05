package synk.meeteam.domain.recruitment.recruitment_role.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.repository.RecruitmentRoleRepository;

@Service
@RequiredArgsConstructor
public class RecruitmentRoleService {
    private final RecruitmentRoleRepository recruitmentRoleRepository;

    @Transactional
    public RecruitmentRole createRecruitmentRoleV2(RecruitmentRole recruitmentRole) {
        return recruitmentRoleRepository.save(recruitmentRole);
    }

    public List<RecruitmentRole> findByRecruitmentPostId(Long recruitmentPostId) {
        return recruitmentRoleRepository.findByPostIdWithSkills(recruitmentPostId);
    }

    @Transactional(readOnly = true)
    public List<RecruitmentRole> findAvailableRecruitmentRole(RecruitmentPost recruitmentPost) {
        List<RecruitmentRole> recruitmentRoles = recruitmentRoleRepository.findAllByRecruitmentPost(
                recruitmentPost);

        return recruitmentRoles.stream()
                .filter(recruitmentRole -> recruitmentRole.isAvailableRecruitmentRole())
                .toList();
    }
}
