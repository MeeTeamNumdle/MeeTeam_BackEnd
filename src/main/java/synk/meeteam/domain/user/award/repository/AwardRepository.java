package synk.meeteam.domain.user.award.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.user.award.entity.Award;

public interface AwardRepository extends JpaRepository<Award, Long> {

    void deleteAllByCreatedBy(Long userId);

    List<Award> findAllByCreatedBy(Long userId);
}
