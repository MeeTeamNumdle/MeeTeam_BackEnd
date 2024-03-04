package synk.meeteam.domain.common.skill.repository;

import java.util.List;
import synk.meeteam.domain.common.skill.dto.SkillDto;

public interface SkillRepositoryCustom {
    List<SkillDto> findAllByKeywordAndTopLimit(String keyword, long limit);
}
