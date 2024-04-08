package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import java.util.List;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitStatus;

public interface RecruitmentApplicantCustomRepository {
    long updateRecruitStatus(List<Long> applicantIds, RecruitStatus recruitStatus);
}
