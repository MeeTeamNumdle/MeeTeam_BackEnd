package synk.meeteam.domain.common.skill.repository;

import static synk.meeteam.domain.common.skill.exception.SkillExceptionType.INVALID_SKILL_ID;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.exception.SkillException;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findById(Long skillId);

    default Skill findByIdOrElseThrowException(Long skillId) {
        return findById(skillId).orElseThrow(() -> new SkillException(INVALID_SKILL_ID));
    }
}
