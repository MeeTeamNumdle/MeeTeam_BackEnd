package synk.meeteam.domain.recruitment.recruitment_role.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;

public interface RecruitmentRoleRepository extends JpaRepository<RecruitmentRole, Long> {
    @Query("SELECT r FROM RecruitmentRole r JOIN FETCH r.recruitmentRoleSkills s JOIN FETCH s.skill WHERE r.recruitmentPost.id = :postId")
    List<RecruitmentRole> findByPostIdWithSkills(@Param("postId") Long postId);
}
