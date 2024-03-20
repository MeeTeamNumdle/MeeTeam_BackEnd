package synk.meeteam.domain.user.award.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.user.award.entity.Award;

public interface AwardRepository extends JpaRepository<Award, Long> {

    void deleteAllByCreatedBy(Long id);
}
