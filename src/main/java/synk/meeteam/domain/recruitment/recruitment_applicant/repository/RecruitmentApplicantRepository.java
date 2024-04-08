package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.entity.User;

public interface RecruitmentApplicantRepository extends JpaRepository<RecruitmentApplicant, Long>,
        RecruitmentApplicantCustomRepository {

    @Query("SELECT a FROM RecruitmentApplicant a JOIN FETCH a.recruitmentPost WHERE a.id IN :ids")
    List<RecruitmentApplicant> findAllInApplicantId(@Param("ids") List<Long> applicantIds);

    Optional<RecruitmentApplicant> findByRecruitmentPostAndApplicant(RecruitmentPost recruitmentPost, User user);
}
