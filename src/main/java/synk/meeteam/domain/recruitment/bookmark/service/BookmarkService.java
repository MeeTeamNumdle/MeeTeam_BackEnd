package synk.meeteam.domain.recruitment.bookmark.service;

import static synk.meeteam.domain.recruitment.bookmark.exception.BookmarkExceptionType.INVALID_BOOKMARK;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.bookmark.entity.Bookmark;
import synk.meeteam.domain.recruitment.bookmark.exception.BookmarkException;
import synk.meeteam.domain.recruitment.bookmark.repository.BookmarkRepository;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.entity.User;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Bookmark bookmarkRecruitmentPost(final RecruitmentPost recruitmentPost, final User user) {
        // 이미 북마크 했는지 검증
        if (isBookmarked(recruitmentPost, user)) {
            throw new BookmarkException(INVALID_BOOKMARK);
        }

        Bookmark bookmark = Bookmark.builder()
                .recruitmentPost(recruitmentPost)
                .user(user)
                .build();
        return bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void cancelBookmarkRecruitmentPost(final RecruitmentPost recruitmentPost, final User user) {
        // 북마크한 기록이 없는지 검증
        if (!isBookmarked(recruitmentPost, user)) {
            throw new BookmarkException(INVALID_BOOKMARK);
        }

        bookmarkRepository.deleteByRecruitmentPostAndUser(recruitmentPost, user);
    }

    public boolean isBookmarked(final RecruitmentPost recruitmentPost, final User user) {
        Bookmark foundBookmark = bookmarkRepository.findByRecruitmentPostAndUser(recruitmentPost,
                user).orElse(null);

        if (foundBookmark == null) {
            return false;
        }
        return true;
    }
}
