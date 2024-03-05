package synk.meeteam.domain.common.skill.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.repository.SkillRepository;


@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public Skill findBySkillId(Long skillId) {
        return skillRepository.findByIdOrElseThrowException(skillId);
    }

    public List<SkillDto> searchByKeyword(String keyword, long limit) {
        return skillRepository.findAllByKeywordAndTopLimit(keyword, limit);
    }
}
