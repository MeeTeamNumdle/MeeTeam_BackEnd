package synk.meeteam.domain.recruitment.recruitment_applicant.service.vo;

import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.INVALID_REQUEST;

import java.util.List;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantException;

public class RecruitmentApplicants {
    private final List<RecruitmentApplicant> recruitmentApplicants;

    public RecruitmentApplicants(List<RecruitmentApplicant> recruitmentApplicants, List<Long> requestApplicantIds,
                                 Long writerId) {
        validateApplicants(recruitmentApplicants, requestApplicantIds);
        validateCanProcess(recruitmentApplicants, writerId);
        this.recruitmentApplicants = recruitmentApplicants;
    }

    public List<Long> getRecruitmentApplicantIds() {
        return recruitmentApplicants.stream()
                .map(RecruitmentApplicant::getId)
                .toList();
    }

    private void validateApplicants(List<RecruitmentApplicant> recruitmentApplicants, List<Long> requestApplicantIds) {
        if (recruitmentApplicants.size() != requestApplicantIds.size()) {
            throw new RecruitmentApplicantException(INVALID_REQUEST);
        }

        List<Long> actualApplicantIds = recruitmentApplicants.stream()
                .map(RecruitmentApplicant::getId)
                .toList();

        for (Long id : requestApplicantIds) {
            if (!actualApplicantIds.contains(id)) {
                throw new RecruitmentApplicantException(INVALID_REQUEST);
            }
        }
    }

    private void validateCanProcess(List<RecruitmentApplicant> applicants, Long userId) {
        // applicants 검증로직
        // 호출한 사용자가 구인글 작성자인지 확인
        // status가 다 NONE인지 확인

        applicants.stream()
                .forEach(applicant -> applicant.validateCanApprove(userId));
    }


}
