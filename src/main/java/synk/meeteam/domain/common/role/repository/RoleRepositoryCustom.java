package synk.meeteam.domain.common.role.repository;

import java.util.List;
import synk.meeteam.domain.common.role.dto.RoleDto;

public interface RoleRepositoryCustom {
    List<RoleDto> findAvailableRoleByRecruitmentId(Long postId);

    List<RoleDto> findAllByKeywordAndTopLimit(String keyword, long limit);
}
