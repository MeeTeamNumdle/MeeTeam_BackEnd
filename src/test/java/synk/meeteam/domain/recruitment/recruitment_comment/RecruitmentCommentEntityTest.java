package synk.meeteam.domain.recruitment.recruitment_comment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import synk.meeteam.domain.recruitment.recruitment_comment.entity.RecruitmentComment;

public class RecruitmentCommentEntityTest {

    @Test
    void 소프트삭제_성공() {
        // given
        RecruitmentComment comment = RecruitmentComment.builder()
                .content("댓글입니다.")
                .isDeleted(false)
                .build();

        // when
        comment.softDelete();

        // then
        Assertions.assertThat(comment.isDeleted()).isEqualTo(true);
    }
}
