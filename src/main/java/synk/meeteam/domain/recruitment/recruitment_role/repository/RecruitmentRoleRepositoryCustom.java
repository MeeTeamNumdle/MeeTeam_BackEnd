package synk.meeteam.domain.recruitment.recruitment_role.repository;

import java.util.List;
import synk.meeteam.domain.recruitment.recruitment_role.dto.AvailableRecruitmentRoleDto;

public interface RecruitmentRoleRepositoryCustom {
    List<AvailableRecruitmentRoleDto> findAvailableRecruitmentRoleByRecruitmentId(Long postId);
}
