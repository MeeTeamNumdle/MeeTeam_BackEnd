package synk.meeteam.domain.user.user_skill.repository;

import java.util.List;
import synk.meeteam.domain.common.skill.dto.SkillDto;

public interface UserSkillCustomRepository {
    List<SkillDto> findSkillDtoByCreatedBy(Long userId);

}
