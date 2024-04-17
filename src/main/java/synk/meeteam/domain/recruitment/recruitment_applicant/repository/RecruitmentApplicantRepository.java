package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.SS_600;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synk.meeteam.global.entity.DeleteStatus;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantException;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.entity.User;

public interface RecruitmentApplicantRepository extends JpaRepository<RecruitmentApplicant, Long>,
        RecruitmentApplicantCustomRepository {

    @Query("SELECT a FROM RecruitmentApplicant a JOIN FETCH a.recruitmentPost JOIN FETCH a.applicant WHERE a.id IN :ids AND a.deleteStatus = synk.meeteam.global.entity.DeleteStatus.ALIVE")
    List<RecruitmentApplicant> findAllInApplicantId(@Param("ids") List<Long> applicantIds);

    Optional<RecruitmentApplicant> findByRecruitmentPostAndApplicantAndDeleteStatus(RecruitmentPost recruitmentPost,
                                                                                    User user,
                                                                                    DeleteStatus deleteStatus);

    default RecruitmentApplicant findByRecruitmentPostAndApplicantOrElseThrow(RecruitmentPost recruitmentPost,
                                                                              User user) {
        return findByRecruitmentPostAndApplicantAndDeleteStatus(recruitmentPost, user, DeleteStatus.ALIVE)
                .orElseThrow(() -> new RecruitmentApplicantException(SS_600));
    }
}
