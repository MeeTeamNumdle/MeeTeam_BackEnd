package synk.meeteam.domain.recruitment.recruitment_applicant.service;

import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.INVALID_REQUEST;
import static synk.meeteam.infra.s3.S3FileName.USER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantException;
import synk.meeteam.domain.recruitment.recruitment_applicant.repository.RecruitmentApplicantRepository;
import synk.meeteam.global.util.Encryption;
import synk.meeteam.infra.s3.service.S3Service;

@Service
@RequiredArgsConstructor
public class RecruitmentApplicantService {
    private final RecruitmentApplicantRepository recruitmentApplicantRepository;
    private final S3Service s3Service;

    @Transactional
    public void registerRecruitmentApplicant(RecruitmentApplicant recruitmentApplicant) {
        recruitmentApplicantRepository.save(recruitmentApplicant);
    }

    @Transactional(readOnly = true)
    public List<RecruitmentApplicant> getAllApplicants(List<Long> applicantIds) {
        return recruitmentApplicantRepository.findAllInApplicantId(applicantIds);
    }

    @Transactional(readOnly = true)
    public List<Long> getRoleIds(List<RecruitmentApplicant> approvedApplicants) {
        return approvedApplicants.stream()
                .map(applicant -> applicant.getRole().getId()).toList();
    }

    @Transactional(readOnly = true)
    public Map<Long, Long> getRecruitedCounts(List<RecruitmentApplicant> approvedApplicants) {
        Map<Long, Long> recruitedRoleCounts = new HashMap<>();
        approvedApplicants.stream()
                .forEach(applicant -> {
                    recruitedRoleCounts.putIfAbsent(applicant.getRole().getId(), 0L);

                    recruitedRoleCounts.put(applicant.getRole().getId(),
                            recruitedRoleCounts.get(applicant.getRole().getId()) + 1);
                });

        return recruitedRoleCounts;
    }

    @Transactional
    public void approveApplicants(List<RecruitmentApplicant> applicants, List<Long> applicantIds, Long userId) {
        validateCanApprove(applicants, userId);
        validateApplicantCount(applicantIds.size(), applicants.size());

        recruitmentApplicantRepository.bulkApprove(applicantIds);
    }

    @Transactional
    public List<GetApplicantResponseDto> getAllByRole(Long postId, Long roleId) {
        List<GetApplicantResponseDto> applicantDtos = recruitmentApplicantRepository.findByPostIdAndRoleId(postId,
                roleId);
        applicantDtos.stream().forEach(applicant -> applicant.setEncryptedUserIdAndProfileImg(
                Encryption.encryptLong(Long.parseLong(applicant.getUserId())),
                s3Service.createPreSignedGetUrl(USER, applicant.getProfileImg())));

        return applicantDtos;
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
