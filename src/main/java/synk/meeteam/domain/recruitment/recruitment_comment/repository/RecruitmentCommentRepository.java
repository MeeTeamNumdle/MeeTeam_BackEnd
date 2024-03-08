package synk.meeteam.domain.recruitment.recruitment_comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.recruitment.recruitment_comment.entity.RecruitmentComment;


public interface RecruitmentCommentRepository extends JpaRepository<RecruitmentComment, Long>,
        RecruitmentCommentRepositoryCustom {

}
