package synk.meeteam.domain.common.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.skill.entity.Skill;

public interface SKillRepository extends JpaRepository<Skill, Long>, SkillRepositoryCustom {

}
