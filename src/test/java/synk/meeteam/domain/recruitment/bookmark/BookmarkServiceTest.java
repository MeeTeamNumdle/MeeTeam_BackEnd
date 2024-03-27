package synk.meeteam.domain.recruitment.bookmark;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static synk.meeteam.domain.recruitment.bookmark.exception.BookmarkExceptionType.INVALID_BOOKMARK;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.recruitment.bookmark.entity.Bookmark;
import synk.meeteam.domain.recruitment.bookmark.exception.BookmarkException;
import synk.meeteam.domain.recruitment.bookmark.repository.BookmarkRepository;
import synk.meeteam.domain.recruitment.bookmark.service.BookmarkService;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.UserFixture;
import synk.meeteam.domain.user.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class BookmarkServiceTest {
    @InjectMocks
    private BookmarkService bookmarkService;
    @Mock
    private BookmarkRepository bookmarkRepository;

    private RecruitmentPost recruitmentPost;
    private User user;
    private Bookmark bookmark;


    @BeforeEach
    void init() {
        user = UserFixture.createUser();
        recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목입니다.");

        bookmark = Bookmark.builder()
                .recruitmentPost(recruitmentPost)
                .user(user)
                .build();
    }

    @Test
    void 구인글북마크_성공() {
        // given
        doReturn(bookmark).when(bookmarkRepository).save(any());
        doReturn(Optional.ofNullable(null)).when(bookmarkRepository)
                .findByRecruitmentPostAndUser(recruitmentPost, user);

        // when
        Bookmark bookmark = bookmarkService.bookmarkRecruitmentPost(recruitmentPost, user);

        // then
        Assertions.assertThat(bookmark)
                .extracting("user", "recruitmentPost")
                .containsExactly(user, recruitmentPost);
    }

    @Test
    void 구인글북마크_예외발생_이미북마크한경우() {
        // given
        doReturn(Optional.ofNullable(bookmark)).when(bookmarkRepository)
                .findByRecruitmentPostAndUser(recruitmentPost, user);

        // when, then
        Assertions.assertThatThrownBy(() -> bookmarkService.bookmarkRecruitmentPost(recruitmentPost, user))
                .isInstanceOf(BookmarkException.class)
                .hasMessageContaining(INVALID_BOOKMARK.message());
    }
}
