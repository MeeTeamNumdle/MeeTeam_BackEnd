package synk.meeteam.domain.recruitment.bookmark.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.bookmark.entity.Bookmark;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.entity.User;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByRecruitmentPostAndUser(RecruitmentPost recruitmentPost, User user);

    void deleteByRecruitmentPostAndUser(RecruitmentPost recruitmentPost, User user);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Bookmark b WHERE b.user.id = :userId")
    void deleteAllByUserId(Long userId);
}
