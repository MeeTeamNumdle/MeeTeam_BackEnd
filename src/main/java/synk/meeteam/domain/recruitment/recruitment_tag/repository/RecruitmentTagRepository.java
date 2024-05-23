package synk.meeteam.domain.recruitment.recruitment_tag.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;

public interface RecruitmentTagRepository extends JpaRepository<RecruitmentTag, Long> {
    @Query("SELECT r FROM RecruitmentTag r JOIN FETCH r.tag JOIN FETCH r.recruitmentPost WHERE r.recruitmentPost.id = :postId")
    List<RecruitmentTag> findByPostIdWithTag(@Param("postId") Long postId);

    void deleteAllByRecruitmentPostId(Long postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RecruitmentTag r WHERE r.recruitmentPost.id IN :postIds")
    void deleteAllByPostIdInQuery(List<Long> postIds);
}
