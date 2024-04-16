package synk.meeteam.domain.recruitment.recruitment_post.repository;

import static synk.meeteam.domain.recruitment.recruitment_post.exception.RecruitmentPostExceptionType.NOT_FOUND_POST;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.exception.RecruitmentPostException;

public interface RecruitmentPostRepository extends JpaRepository<RecruitmentPost, Long>,
        RecruitmentPostSearchRepository, RecruitmentManagementRepository {

    @Query("SELECT r FROM RecruitmentPost r WHERE r.id = :id AND r.deleteStatus = synk.meeteam.global.entity.DeleteStatus.ALIVE")
    Optional<RecruitmentPost> findByIdAndDeleteStatus(@Param("id") Long postId);

    default RecruitmentPost findByIdOrElseThrow(Long postId) {
        return findByIdAndDeleteStatus(postId).orElseThrow(() -> new RecruitmentPostException(NOT_FOUND_POST));
    }
}
