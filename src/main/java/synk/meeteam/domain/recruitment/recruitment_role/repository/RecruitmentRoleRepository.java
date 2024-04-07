package synk.meeteam.domain.recruitment.recruitment_role.repository;

import static synk.meeteam.domain.recruitment.recruitment_role.exception.RecruitmentRoleExceptionType.INVALID_RECRUITMENT_ROLE_ID;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.exception.RecruitmentRoleException;

public interface RecruitmentRoleRepository extends JpaRepository<RecruitmentRole, Long>,
        RecruitmentRoleRepositoryCustom {
    @Query("SELECT r FROM RecruitmentRole r JOIN FETCH r.recruitmentRoleSkills s JOIN FETCH s.skill WHERE r.recruitmentPost.id = :postId")
    List<RecruitmentRole> findByPostIdWithSkills(@Param("postId") Long postId);

    @Query("SELECT r FROM RecruitmentRole r JOIN FETCH r.recruitmentPost p JOIN FETCH r.role WHERE p.id = :postId")
    List<RecruitmentRole> findAllByPostIdWithRecruitmentRoleAndRole(@Param("postId") Long postId);

    @Query("SELECT r FROM RecruitmentRole r JOIN FETCH r.recruitmentPost p JOIN FETCH r.role WHERE r.id = :recruitmentRoleId")
    Optional<RecruitmentRole> findByIdWithRecruitmentRoleAndRole(@Param("recruitmentRoleId") Long recruitmentRoleId);

    default RecruitmentRole findByIdWithRecruitmentRoleAndRoleOrElseThrow(Long recruitmentRoleId) {
        return findByIdWithRecruitmentRoleAndRole(recruitmentRoleId).orElseThrow(
                () -> new RecruitmentRoleException(INVALID_RECRUITMENT_ROLE_ID));
    }

    void deleteAllByRecruitmentPostId(Long postId);

    @Query("SELECT r FROM RecruitmentRole r JOIN FETCH r.recruitmentPost p JOIN FETCH r.role t WHERE p.id = :postId AND t.id IN :roleIds")
    List<RecruitmentRole> findAllByPostIdAndRoleIds(@Param("postId") Long postId, @Param("roleIds") List<Long> roleIds);
}
