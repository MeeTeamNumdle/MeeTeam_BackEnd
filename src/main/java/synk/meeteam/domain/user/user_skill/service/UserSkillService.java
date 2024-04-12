package synk.meeteam.domain.user.user_skill.service;

import java.util.List;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.user.user_skill.entity.UserSkill;

public interface UserSkillService {
    List<UserSkill> changeUserSkillBySkill(Long userId, List<Long> skillIds);

    List<SkillDto> getUserSKill(Long userId);
}
