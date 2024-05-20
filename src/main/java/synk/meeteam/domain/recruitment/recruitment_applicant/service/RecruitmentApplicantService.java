package synk.meeteam.domain.recruitment.recruitment_applicant.service;

import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.INVALID_REQUEST;
import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.SS_602;
import static synk.meeteam.infra.aws.S3FilePath.USER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitStatus;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantException;
import synk.meeteam.domain.recruitment.recruitment_applicant.repository.RecruitmentApplicantRepository;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.dto.SliceInfo;
import synk.meeteam.global.entity.DeleteStatus;
import synk.meeteam.global.util.Encryption;
import synk.meeteam.infra.aws.service.S3Service;

@Service
@RequiredArgsConstructor
public class RecruitmentApplicantService {
    private final RecruitmentApplicantRepository recruitmentApplicantRepository;
    private final S3Service s3Service;

    @Transactional
    public void registerRecruitmentApplicant(RecruitmentApplicant recruitmentApplicant) {
        // 이미 신청한 경우 검증로직
        validateAlreadyApply(recruitmentApplicant);

        recruitmentApplicantRepository.save(recruitmentApplicant);
    }

    @Transactional
    public void cancelRegisterRecruitmentApplicant(RecruitmentApplicant recruitmentApplicant) {
        recruitmentApplicant.softDelete();
    }

    @Transactional(readOnly = true)
    public List<RecruitmentApplicant> getAllApplicants(List<Long> applicantIds) {
        return recruitmentApplicantRepository.findAllInApplicantId(applicantIds);
    }

    @Transactional(readOnly = true)
    public RecruitmentApplicant getApplicant(RecruitmentPost recruitmentPost, User user) {
        return recruitmentApplicantRepository.findByRecruitmentPostAndApplicantOrElseThrow(recruitmentPost, user);
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
        validateCanProcess(applicants, userId);
        validateApplicantCount(applicantIds.size(), applicants.size());

        recruitmentApplicantRepository.updateRecruitStatus(applicantIds, RecruitStatus.APPROVED);
    }

    @Transactional
    public void rejectApplicants(List<RecruitmentApplicant> applicants, List<Long> applicantIds, Long userId) {
        validateCanProcess(applicants, userId);
        validateApplicantCount(applicantIds.size(), applicants.size());

        recruitmentApplicantRepository.updateRecruitStatus(applicantIds, RecruitStatus.REJECTED);
    }

    @Transactional
    public GetApplicantResponseDto getAllByRole(Long postId, Long roleId, int page, int size) {
        int pageNumber = page - 1;
        Pageable pageable = PageRequest.of(pageNumber, size);

        Slice<GetApplicantDto> applicantDtos = recruitmentApplicantRepository.findByPostIdAndRoleId(postId,
                roleId, pageable);
        applicantDtos.stream().forEach(applicant -> applicant.setEncryptedUserIdAndProfileImg(
                Encryption.encryptLong(Long.parseLong(applicant.getUserId())),
                s3Service.createPreSignedGetUrl(USER, applicant.getProfileImg())));

        SliceInfo pageInfo = new SliceInfo(page, size, applicantDtos.hasNext());
        return new GetApplicantResponseDto(applicantDtos.getContent(), pageInfo);
    }

    @Transactional(readOnly = true)
    public boolean isAppliedUser(RecruitmentPost recruitmentPost, User user) {
        RecruitmentApplicant recruitmentApplicant = recruitmentApplicantRepository.findByRecruitmentPostAndApplicantAndDeleteStatus(
                recruitmentPost, user, DeleteStatus.ALIVE).orElse(null);

        if (recruitmentApplicant != null) {
            return false;
        }
        return true;
    }

    private void validateCanProcess(List<RecruitmentApplicant> applicants, Long userId) {
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

    private void validateAlreadyApply(RecruitmentApplicant recruitmentApplicant) {
        RecruitmentApplicant alreadyAppliedUser = recruitmentApplicantRepository.findByRecruitmentPostAndApplicantAndDeleteStatus(
                        recruitmentApplicant.getRecruitmentPost(), recruitmentApplicant.getApplicant(), DeleteStatus.ALIVE)
                .orElse(null);
        if (alreadyAppliedUser != null) {
            throw new RecruitmentApplicantException(SS_602);
        }
    }
}
