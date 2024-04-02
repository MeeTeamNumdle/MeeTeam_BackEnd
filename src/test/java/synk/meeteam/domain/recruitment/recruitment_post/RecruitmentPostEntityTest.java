package synk.meeteam.domain.recruitment.recruitment_post;


import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.recruitment.bookmark.exception.BookmarkException;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.exception.RecruitmentPostException;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;

public class RecruitmentPostEntityTest {

    @Test
    void 구인글마감_성공() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목");
        Long requestUserId = 2L;
        recruitmentPost.setCreatedBy(requestUserId);

        // when, then
        recruitmentPost.closeRecruitmentPost(requestUserId);
    }

    @Test
    void 구인글마감_예외발생_구인글작성자가아닌경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목");
        Long requestUserId = -1L;
        recruitmentPost.setCreatedBy(2L);

        // when, then
        Assertions.assertThatThrownBy(
                        () -> recruitmentPost.closeRecruitmentPost(requestUserId))
                .isInstanceOf(RecruitmentPostException.class);
    }

    @Test
    void 구인글수정_성공() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목");
        Long requestUserId = 1L;
        recruitmentPost.setCreatedBy(requestUserId);

        // when
        recruitmentPost.updateRecruitmentPost("정상제목2", "내용", Scope.ON_CAMPUS, Category.PROJECT, new Field(1L, "개발"),
                ProceedType.ON_LINE, LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 15), LocalDate.of(2024, 1, 15), 5L,
                "kakaolink~~", false, null, 5L, 5L);

        // then
        Assertions.assertThat(recruitmentPost)
                .extracting("title", "content", "scope", "category")
                .containsExactly("정상제목2", "내용", Scope.ON_CAMPUS, Category.PROJECT);
    }

    @Test
    void 북마크수증가_성공() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목");
        long curBookmarkCnt = recruitmentPost.getBookmarkCount();

        // when
        RecruitmentPost newRecruitmentPost = recruitmentPost.incrementBookmarkCount();

        // then
        Assertions.assertThat(newRecruitmentPost.getBookmarkCount()).isEqualTo(curBookmarkCnt + 1);
    }

    @Test
    void 북마크수감소_성공() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목");
        long curBookmarkCnt = recruitmentPost.getBookmarkCount();

        // when
        RecruitmentPost newRecruitmentPost = recruitmentPost.decrementBookmarkCount();

        // then
        Assertions.assertThat(newRecruitmentPost.getBookmarkCount()).isEqualTo(curBookmarkCnt - 1);
    }

    @Test
    void 북마크수감소_예외발생_북마크수가0이하일경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost_bookmark(0L);

        // when, then
        Assertions.assertThatThrownBy(() -> recruitmentPost.decrementBookmarkCount())
                .isInstanceOf(BookmarkException.class);
    }

    @Test
    void 링크설정_성공() {
        // given
        String kakaoLink = "http://카카오링크입니다";
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상 제목입니다.");

        // when
        RecruitmentPost updatedRecruitmentPost = recruitmentPost.setLink(kakaoLink);

        // then
        Assertions.assertThat(updatedRecruitmentPost.getKakaoLink()).isEqualTo(kakaoLink);

    }

}
