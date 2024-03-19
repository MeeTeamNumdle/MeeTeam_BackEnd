package synk.meeteam.domain.recruitment.recruitment_post;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.exception.RecruitmentPostException;

public class RecruitmentPostEntityTest {

    @Test
    void 구인글작성자검증_성공() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목");
        Long requestUserId = 2L;
        recruitmentPost.setCreatedBy(requestUserId);

        // when, then
        recruitmentPost.validateWriter(requestUserId);
    }

    @Test
    void 구인글작성자검증_예외발생_구인글작성자가아닌경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상제목");
        Long requestUserId = -1L;
        recruitmentPost.setCreatedBy(2L);

        // when, then
        Assertions.assertThatThrownBy(
                        () -> recruitmentPost.validateWriter(requestUserId))
                .isInstanceOf(RecruitmentPostException.class);
    }

}
