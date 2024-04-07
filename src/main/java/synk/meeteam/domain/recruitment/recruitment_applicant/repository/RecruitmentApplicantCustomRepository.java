package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import java.util.List;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantResponseDto;

public interface RecruitmentApplicantCustomRepository {
    long bulkApprove(List<Long> applicantIds);

    List<GetApplicantResponseDto> findByRoleQuery(Long postId, Long roleId);
}
