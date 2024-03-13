package synk.meeteam.domain.user.award.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.user.award.entity.Award;
import synk.meeteam.domain.user.user.entity.User;

public interface AwardRepository extends JpaRepository<Award, Long> {

    void deleteAllByUser(User user);
}
