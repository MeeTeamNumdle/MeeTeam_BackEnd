package synk.meeteam.domain.recruitment.recruitment_post;

import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE_EXCEED_40;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;

@DataJpaTest
public class RecruitmentPostRepositoryTest {

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    @Test
    public void 구인글생성_구인글생성성공_정상입력일경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("정상적인 제목");

        // when
        RecruitmentPost savedRecruitmentPost = recruitmentPostRepository.save(recruitmentPost);
        RecruitmentPost foundRecruitmentPost = recruitmentPostRepository.findById(
                savedRecruitmentPost.getId()).orElse(null);

        // then
        Assertions.assertThat(foundRecruitmentPost)
                .extracting("title", "content", "scope", "category", "field", "proceedType", "proceedingStart",
                        "proceedingEnd", "deadline")
                .containsExactly(recruitmentPost.getTitle(), recruitmentPost.getContent(), recruitmentPost.getScope(),
                        recruitmentPost.getCategory(), recruitmentPost.getField(), recruitmentPost.getProceedType(),
                        recruitmentPost.getProceedingStart(), recruitmentPost.getProceedingEnd(),
                        recruitmentPost.getDeadline());
    }

    @Test
    public void 구인글생성_예외발생_제목이null인경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(null);

        // when, then
        Assertions.assertThatThrownBy(() -> {
            recruitmentPostRepository.save(recruitmentPost);
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void 구인글생성_예외발생_제목이40자넘는경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE_EXCEED_40);

        // when, then
        Assertions.assertThatThrownBy(() -> {
            recruitmentPostRepository.save(recruitmentPost);
        }).isInstanceOf(ConstraintViolationException.class);
    }

}
