package synk.meeteam.domain.user.award.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.user.award.entity.Award;

public interface AwardRepository extends JpaRepository<Award, Long> {

    void deleteAllByCreatedBy(Long userId);

    List<Award> findAllByCreatedBy(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Award a WHERE a.createdBy = :userId")
    void deleteAllByUserId(Long userId);
}
