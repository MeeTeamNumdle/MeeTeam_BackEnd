package synk.meeteam.domain.user.user_skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.user.user_skill.entity.UserSkill;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long>, UserSkillCustomRepository {

    void deleteAllByCreatedBy(Long id);
}
