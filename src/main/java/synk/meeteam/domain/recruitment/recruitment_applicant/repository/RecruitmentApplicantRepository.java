package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;

public interface RecruitmentApplicantRepository extends JpaRepository<RecruitmentApplicant, Long> {
}
