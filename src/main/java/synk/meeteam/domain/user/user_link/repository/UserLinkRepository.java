package synk.meeteam.domain.user.user_link.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.user.user_link.entity.UserLink;

public interface UserLinkRepository extends JpaRepository<UserLink, Long> {

    void deleteAllByCreatedBy(Long userId);

    List<UserLink> findAllByCreatedBy(Long userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM UserLink u WHERE u.createdBy = :userId")
    void deleteAllByUserId(Long userId);
}
