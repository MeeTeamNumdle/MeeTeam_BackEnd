package synk.meeteam.domain.recruitment.recruitment_comment.repository;

import static synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentExceptionType.INVALID_COMMENT;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.recruitment.recruitment_comment.entity.RecruitmentComment;
import synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentException;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;


public interface RecruitmentCommentRepository extends JpaRepository<RecruitmentComment, Long>,
        RecruitmentCommentRepositoryCustom {

    Optional<RecruitmentComment> findFirstByRecruitmentPostOrderByGroupNumberDesc(RecruitmentPost recruitmentPost);

    Optional<RecruitmentComment> findFirstByRecruitmentPostAndGroupNumberOrderByGroupOrder(
            RecruitmentPost recruitmentPost, long groupNumber);

    default RecruitmentComment findLatestGroupOrderOrElseThrow(RecruitmentPost recruitmentPost, long groupNumber) {
        return findFirstByRecruitmentPostAndGroupNumberOrderByGroupOrder(recruitmentPost, groupNumber).orElseThrow(
                () -> new RecruitmentCommentException(INVALID_COMMENT));
    }
}
