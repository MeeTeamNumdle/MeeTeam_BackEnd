package synk.meeteam.domain.user.award.service;

import java.util.List;
import synk.meeteam.domain.user.award.dto.UpdateAwardDto;
import synk.meeteam.domain.user.award.entity.Award;

public interface AwardService {
    List<Award> changeAward(Long id, List<UpdateAwardDto> awardDtos);
}
