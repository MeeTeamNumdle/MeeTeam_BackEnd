package synk.meeteam.domain.recruitment.recruitment_applicant.service;

import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.INVALID_REQUEST;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantException;
import synk.meeteam.domain.recruitment.recruitment_applicant.repository.RecruitmentApplicantRepository;

@Service
@RequiredArgsConstructor
public class RecruitmentApplicantService {
    private final RecruitmentApplicantRepository recruitmentApplicantRepository;

    @Transactional
    public void registerRecruitmentApplicant(RecruitmentApplicant recruitmentApplicant) {
        recruitmentApplicantRepository.save(recruitmentApplicant);
    }

    @Transactional(readOnly = true)
    public List<RecruitmentApplicant> getAllApplicants(List<Long> applicantIds) {
        return recruitmentApplicantRepository.findAllById(applicantIds);
    }

    @Transactional
    public void approveApplicants(List<Long> applicantIds, List<RecruitmentApplicant> applicants, Long userId) {
        validateCanApprove(applicants, userId);
        validateApplicantCount(applicantIds.size(), applicants.size());

        recruitmentApplicantRepository.bulkApprove(applicantIds);
    }

    private void validateCanApprove(List<RecruitmentApplicant> applicants, Long userId) {
        // applicants 검증로직
        // 호출한 사용자가 구인글 작성자인지 확인
        // status가 다 NONE인지 확인

        applicants.stream()
                .forEach(applicant -> applicant.validateCanApprove(userId));
    }

    private void validateApplicantCount(int requestCount, int actualCount) {
        if (requestCount != actualCount) {
            throw new RecruitmentApplicantException(INVALID_REQUEST);
        }
    }
}
