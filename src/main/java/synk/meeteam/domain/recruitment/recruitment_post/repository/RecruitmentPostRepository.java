package synk.meeteam.domain.recruitment.recruitment_post.repository;

import static synk.meeteam.domain.recruitment.recruitment_post.exception.RecruitmentPostExceptionType.NOT_FOUND_POST;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.exception.RecruitmentPostException;

public interface RecruitmentPostRepository extends JpaRepository<RecruitmentPost, Long> {

    default RecruitmentPost findByIdOrElseThrow(Long postId) {
        return findById(postId).orElseThrow(() -> new RecruitmentPostException(NOT_FOUND_POST));
    }
}
