package synk.meeteam.domain.recruitment.recruitment_post;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.exception.RecruitmentPostException;

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
    void 북마크수증가_성공() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목");
        long curBookmarkCnt = recruitmentPost.getBookmarkCount();

        // when
        RecruitmentPost newRecruitmentPost = recruitmentPost.incrementBookmarkCount();

        // then
        Assertions.assertThat(newRecruitmentPost.getBookmarkCount()).isEqualTo(curBookmarkCnt + 1);
    }

}
