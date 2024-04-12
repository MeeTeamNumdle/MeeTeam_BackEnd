package synk.meeteam.domain.recruitment.recruitment_comment;

import static synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentExceptionType.INVALID_USER;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import synk.meeteam.domain.recruitment.recruitment_comment.entity.RecruitmentComment;
import synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentException;

public class RecruitmentCommentEntityTest {

    @Test
    void 소프트삭제_성공() {
        // given
        Long userId = 1L;
        RecruitmentComment comment = RecruitmentComment.builder()
                .content("댓글입니다.")
                .isDeleted(false)
                .build();
        comment.setCreatedBy(userId);

        // when
        comment.softDelete(userId);

        // then
        Assertions.assertThat(comment.isDeleted()).isEqualTo(true);
    }

    @Test
    void 소프트삭제_예외발생_작성자가아닌경우() {
        // given
        Long userId = 1L;
        RecruitmentComment comment = RecruitmentComment.builder()
                .content("댓글입니다.")
                .isDeleted(false)
                .build();
        comment.setCreatedBy(0L);

        // when, then
        Assertions.assertThatThrownBy(() -> comment.softDelete(userId))
                .isInstanceOf(RecruitmentCommentException.class)
                .hasMessageContaining(INVALID_USER.message());
    }

    @Test
    void 댓글수정_성공() {
        // given
        Long userId = 1L;
        RecruitmentComment comment = RecruitmentComment.builder()
                .content("댓글입니다.")
                .isDeleted(false)
                .build();
        comment.setCreatedBy(userId);

        // when
        comment.modifyContent("수정내용입니다.", userId);

        // then
        Assertions.assertThat(comment.getContent()).isEqualTo("수정내용입니다.");
    }

    @Test
    void 댓글수정_예외발생_작성자가아닌경우() {
        // given
        Long userId = 1L;
        RecruitmentComment comment = RecruitmentComment.builder()
                .content("댓글입니다.")
                .isDeleted(false)
                .build();
        comment.setCreatedBy(0L);

        // when, then
        Assertions.assertThatThrownBy(() -> comment.modifyContent("수정내용입니다.", userId))
                .isInstanceOf(RecruitmentCommentException.class)
                .hasMessageContaining(INVALID_USER.message());
    }
}
