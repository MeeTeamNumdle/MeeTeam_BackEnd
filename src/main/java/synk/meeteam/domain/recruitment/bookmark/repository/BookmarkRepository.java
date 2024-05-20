package synk.meeteam.domain.recruitment.bookmark.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.recruitment.bookmark.entity.Bookmark;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.entity.User;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByRecruitmentPostAndUser(RecruitmentPost recruitmentPost, User user);

    void deleteByRecruitmentPostAndUser(RecruitmentPost recruitmentPost, User user);

    void deleteAllByUser(User user);
}
