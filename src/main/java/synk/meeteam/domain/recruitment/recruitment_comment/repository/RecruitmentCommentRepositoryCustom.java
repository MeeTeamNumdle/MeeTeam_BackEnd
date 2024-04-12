package synk.meeteam.domain.recruitment.recruitment_comment.repository;

import java.util.List;
import synk.meeteam.domain.recruitment.recruitment_comment.service.vo.RecruitmentCommentVO;

public interface RecruitmentCommentRepositoryCustom {
    List<RecruitmentCommentVO> findAllByRecruitmentId(Long postId);
}
