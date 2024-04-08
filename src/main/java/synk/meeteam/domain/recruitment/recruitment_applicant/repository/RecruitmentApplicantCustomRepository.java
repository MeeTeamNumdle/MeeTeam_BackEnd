package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import java.util.List;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitStatus;

public interface RecruitmentApplicantCustomRepository {
    long updateRecruitStatus(List<Long> applicantIds, RecruitStatus recruitStatus);

    List<GetApplicantResponseDto> findByPostIdAndRoleId(Long postId, Long roleId);
}
