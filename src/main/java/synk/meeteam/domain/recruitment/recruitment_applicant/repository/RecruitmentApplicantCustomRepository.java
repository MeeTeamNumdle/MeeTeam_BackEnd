package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import java.util.List;

public interface RecruitmentApplicantCustomRepository {
    long bulkApprove(List<Long> applicantIds);
}
