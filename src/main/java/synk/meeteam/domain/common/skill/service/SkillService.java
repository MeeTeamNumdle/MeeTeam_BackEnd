package synk.meeteam.domain.common.skill.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.repository.SkillRepository;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public Skill findBySkillId(Long skillId) {
        return skillRepository.findByIdOrElseThrowException(skillId);
    }
}
