package synk.meeteam.domain.recruitment.recruitment_comment;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.recruitment.recruitment_comment.entity.RecruitmentComment;
import synk.meeteam.domain.recruitment.recruitment_comment.repository.RecruitmentCommentRepository;
import synk.meeteam.domain.recruitment.recruitment_comment.service.vo.RecruitmentCommentVO;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;

@DataJpaTest
@ActiveProfiles("test")
public class RecruitmentCommentRepositoryTest {
    private static String CONTENT_EXCEED_100 = "이 내용은 100자가 넘는 내용입니다.이 내용은 100자가 넘는 내용입니다.이 내용은 100자가 넘는 내용입니다.이 내용은 100자가 넘는 내용입니다.이 내용은 100자가 넘는 내용";

    @Autowired
    private RecruitmentCommentRepository recruitmentCommentRepository;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    @Test
    void 댓글조회_댓글반환_구인글id로조회() {
        // given(data.sql)
        Long postId = 1L;

        // when
        List<RecruitmentCommentVO> commentVOs = recruitmentCommentRepository.findAllByRecruitmentId(postId);

        // then
        for (RecruitmentCommentVO vo : commentVOs) {
            Assertions.assertThat(vo.getNickname()).isEqualTo("송민규짱짱맨");
        }

    }

    @Test
    void 댓글등록_예외발생_댓글내용100자넘는경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("제목입니당");
        recruitmentPostRepository.save(recruitmentPost);
        long groupNumber = 1L;
        long groupOrder = 1L;

        RecruitmentComment comment = RecruitmentComment.builder()
                .content(CONTENT_EXCEED_100)
                .isParent(true)
                .recruitmentPost(recruitmentPost)
                .groupNumber(groupNumber)
                .groupOrder(groupOrder)
                .build();

        // when, then
        Assertions.assertThatThrownBy(() -> recruitmentCommentRepository.save(comment))
                .isInstanceOf(ConstraintViolationException.class);

        // then
    }
}
