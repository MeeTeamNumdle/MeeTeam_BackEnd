package synk.meeteam.domain.recruitment.recruitment_post;


import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import synk.meeteam.domain.common.field.entity.Field;
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

}
