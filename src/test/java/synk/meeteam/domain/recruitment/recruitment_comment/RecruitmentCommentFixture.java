package synk.meeteam.domain.recruitment.recruitment_comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.recruitment.recruitment_comment.service.vo.RecruitmentCommentVO;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;

public class RecruitmentCommentFixture {

    public static List<RecruitmentCommentVO> createRecruitmentComments(RecruitmentPost recruitmentPost) {
        List<RecruitmentCommentVO> recruitmentComments = new ArrayList<>();

        recruitmentComments
                .add(RecruitmentCommentVO.builder()
                        .nickname("닉네임입니다2")
                        .userId(2L)
                        .profileImg("wwww.img2")
                        .content("내용입니다2")
                        .createAt(LocalDateTime.of(2024, 3, 5, 3, 12))
                        .isParent(true)
                        .groupNumber(1)
                        .groupOrder(1)
                        .build());

        recruitmentComments
                .add(RecruitmentCommentVO.builder()
                        .nickname("닉네임입니다")
                        .userId(1L)
                        .profileImg("wwww.img")
                        .content("내용입니다")
                        .createAt(LocalDateTime.of(2024, 3, 5, 3, 15))
                        .isParent(false)
                        .groupNumber(1)
                        .groupOrder(2)
                        .build());

        recruitmentComments
                .add(RecruitmentCommentVO.builder()
                        .nickname("닉네임입니다3")
                        .userId(3L)
                        .profileImg("wwww.img3")
                        .content("내용입니다3")
                        .createAt(LocalDateTime.of(2024, 3, 5, 3, 17))
                        .isParent(false)
                        .groupNumber(1)
                        .groupOrder(3)
                        .build());

        recruitmentComments
                .add(RecruitmentCommentVO.builder()
                        .nickname("닉네임입니다3")
                        .userId(3L)
                        .profileImg("wwww.img3")
                        .content("내용입니다4")
                        .createAt(LocalDateTime.of(2024, 3, 5, 3, 15))
                        .isParent(true)
                        .groupNumber(2)
                        .groupOrder(1)
                        .build());

        return recruitmentComments;
    }
}
