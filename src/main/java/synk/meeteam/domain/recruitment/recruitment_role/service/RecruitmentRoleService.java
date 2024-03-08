package synk.meeteam.domain.recruitment.recruitment_role.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.repository.RecruitmentRoleRepository;

@Service
@RequiredArgsConstructor
public class RecruitmentRoleService {
    private final RecruitmentRoleRepository recruitmentRoleRepository;

    @Transactional
    public RecruitmentRole createRecruitmentRole(RecruitmentRole recruitmentRole) {
        return recruitmentRoleRepository.save(recruitmentRole);
    }

    public List<RecruitmentRole> findByRecruitmentPostId(Long recruitmentPostId) {
        return recruitmentRoleRepository.findByPostIdWithSkills(recruitmentPostId);
    }
}
