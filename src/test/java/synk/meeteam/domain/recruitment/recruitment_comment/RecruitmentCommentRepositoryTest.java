package synk.meeteam.domain.recruitment.recruitment_comment;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.recruitment.recruitment_comment.repository.RecruitmentCommentRepository;
import synk.meeteam.domain.recruitment.recruitment_comment.service.vo.RecruitmentCommentVO;

@DataJpaTest
@ActiveProfiles("test")
public class RecruitmentCommentRepositoryTest {
    @Autowired
    private RecruitmentCommentRepository recruitmentCommentRepository;

    @Test
    void 댓글조회_댓글반환_구인글id로조회() {
        // given(data.sql)
        Long postId = 1L;

        // when
        List<RecruitmentCommentVO> commentVOS = recruitmentCommentRepository.findAllByRecruitmentId(postId);

        // then
        for (RecruitmentCommentVO vo : commentVOS) {
            Assertions.assertThat(vo.getNickname()).isEqualTo("송민규짱짱맨");
        }

    }
}
