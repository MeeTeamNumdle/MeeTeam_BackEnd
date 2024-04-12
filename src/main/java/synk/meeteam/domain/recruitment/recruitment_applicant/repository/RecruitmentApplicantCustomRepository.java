package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitStatus;

public interface RecruitmentApplicantCustomRepository {
    long updateRecruitStatus(List<Long> applicantIds, RecruitStatus recruitStatus);

    Slice<GetApplicantDto> findByPostIdAndRoleId(Long postId, Long roleId, Pageable pageable);
}
