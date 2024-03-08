package synk.meeteam.domain.recruitment.recruitment_comment;

import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.recruitment.recruitment_comment.repository.RecruitmentCommentRepository;
import synk.meeteam.domain.recruitment.recruitment_comment.service.RecruitmentCommentService;
import synk.meeteam.domain.recruitment.recruitment_comment.service.vo.RecruitmentCommentVO;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetCommentResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;

@ExtendWith(MockitoExtension.class)
public class RecruitmentCommentServiceTest {

    @InjectMocks
    private RecruitmentCommentService recruitmentCommentService;

    @Mock
    private RecruitmentCommentRepository recruitmentCommentRepository;

    @Test
    void 댓글조회_댓글그룹반환() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost("제목입니당");
        recruitmentPost.setId(1L);
        recruitmentPost.setCreatedBy(1L);
        List<RecruitmentCommentVO> recruitmentComments = RecruitmentCommentFixture.createRecruitmentComments(
                recruitmentPost);
        doReturn(recruitmentComments).when(recruitmentCommentRepository)
                .findAllByRecruitmentId(recruitmentPost.getId());

        // when
        List<GetCommentResponseDto> getCommentResponseDtos = recruitmentCommentService.getRecruitmentComments(
                recruitmentPost);

        // then
        Assertions.assertThat(getCommentResponseDtos.get(0))
                .extracting("nickname", "isWriter")
                .containsExactly("닉네임입니다2", false);

        Assertions.assertThat(getCommentResponseDtos.get(0).replies().get(0))
                .extracting("nickname", "isWriter")
                .containsExactly("닉네임입니다", true);
        Assertions.assertThat(getCommentResponseDtos.get(0).replies().get(1))
                .extracting("nickname", "isWriter")
                .containsExactly("닉네임입니다3", false);

        Assertions.assertThat(getCommentResponseDtos.get(1))
                .extracting("nickname", "isWriter")
                .containsExactly("닉네임입니다3", false);

    }
}
