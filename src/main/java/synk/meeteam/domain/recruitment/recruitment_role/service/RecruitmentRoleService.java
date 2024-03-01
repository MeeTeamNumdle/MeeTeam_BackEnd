package synk.meeteam.domain.recruitment.recruitment_role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.repository.RoleRepository;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.repository.RecruitmentRoleRepository;

@Service
@RequiredArgsConstructor
public class RecruitmentRoleService {
    private final RecruitmentRoleRepository recruitmentRoleRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public RecruitmentRole createRecruitmentRoleV1(Long roleId, RecruitmentPost recruitmentPost, int count) {
        Role foundRole = roleRepository.findByIdOrElseThrowException(roleId);

        RecruitmentRole recruitmentRole = RecruitmentRole.builder()
                .recruitmentPost(recruitmentPost)
                .count(count)
                .role(foundRole)
                .build();
        return recruitmentRoleRepository.save(recruitmentRole);
    }

    @Transactional
    public RecruitmentRole createRecruitmentRoleV2(RecruitmentRole recruitmentRole) {
        return recruitmentRoleRepository.save(recruitmentRole);
    }
}
