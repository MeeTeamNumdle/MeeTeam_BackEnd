package synk.meeteam.domain.user.user_skill.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.user.user_skill.entity.UserSkill;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long>, UserSkillCustomRepository {

    void deleteAllByCreatedBy(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserSkill u WHERE u.createdBy = :userId")
    void deleteAllByUserId(Long userId);
}
