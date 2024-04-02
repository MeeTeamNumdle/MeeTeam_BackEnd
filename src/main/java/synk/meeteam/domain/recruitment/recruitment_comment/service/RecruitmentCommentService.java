package synk.meeteam.domain.recruitment.recruitment_comment.service;

import static synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentExceptionType.INVALID_COMMENT;
import static synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentExceptionType.INVALID_USER;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.recruitment_comment.entity.RecruitmentComment;
import synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentException;
import synk.meeteam.domain.recruitment.recruitment_comment.repository.RecruitmentCommentRepository;
import synk.meeteam.domain.recruitment.recruitment_comment.service.vo.RecruitmentCommentVO;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetCommentResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetReplyResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.global.util.Encryption;
import synk.meeteam.infra.s3.S3FileName;
import synk.meeteam.infra.s3.service.S3Service;

@Service
@RequiredArgsConstructor
public class RecruitmentCommentService {
    private static final String DELETE_MESSAGE = "삭제된 댓글입니다.";

    private final RecruitmentCommentRepository recruitmentCommentRepository;
    private final S3Service s3Service;

    // 가공된 형태를 많이 사용할 것 같다.
    // 그래서 Dto를 바로 반환하는 식으로 만들었다.
    public List<GetCommentResponseDto> getRecruitmentComments(RecruitmentPost recruitmentPost) {
        List<RecruitmentCommentVO> commentVOs = recruitmentCommentRepository.findAllByRecruitmentId(
                recruitmentPost.getId());

        List<GetCommentResponseDto> groupedComments = new ArrayList<>();
        Long writerId = recruitmentPost.getCreatedBy();

        for (RecruitmentCommentVO comment : commentVOs) {
            boolean isWriter = writerId.equals(comment.getUserId());
            String profileImg = s3Service.createPreSignedGetUrl(
                    S3FileName.USER,
                    comment.getProfileImg());
            if (comment.isParent()) {
                List<GetReplyResponseDto> replies = new ArrayList<>();
                groupedComments.add(
                        new GetCommentResponseDto(comment.getId(), Encryption.encryptLong(comment.getUserId()),
                                comment.getNickname(), profileImg,
                                comment.getContent(), comment.getCreateAt(), isWriter, comment.getGroupNumber(),
                                comment.getGroupOrder(), replies));
                continue;
            }

            groupedComments.get(groupedComments.size() - 1).replies()
                    .add(new GetReplyResponseDto(comment.getId(), Encryption.encryptLong(comment.getUserId()),
                            comment.getNickname(), profileImg, comment.getContent(), comment.getCreateAt(),
                            isWriter, comment.getGroupOrder()));
        }

        return groupedComments;
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId, RecruitmentPost recruitmentPost) {
        RecruitmentComment recruitmentComment = recruitmentCommentRepository.findByIdOrElseThrow(commentId);

        // 검증 로직
        if (!recruitmentComment.getCreatedBy().equals(userId)) {
            throw new RecruitmentCommentException(INVALID_USER);
        }

        RecruitmentComment latestRecruitmentComment = recruitmentCommentRepository.findLatestGroupOrderOrElseThrow(
                recruitmentPost, recruitmentComment.getGroupNumber());

        if (recruitmentComment.isParent()
                && (latestRecruitmentComment.getGroupOrder() == recruitmentComment.getGroupOrder())) {

            recruitmentComment.softDelete();
            return;
        }

        recruitmentCommentRepository.delete(recruitmentComment);
    }

    @Transactional
    public RecruitmentComment registerRecruitmentComment(RecruitmentComment recruitmentComment) {
        validateComment(recruitmentComment);

        long groupNumber = recruitmentComment.getGroupNumber();
        if (groupNumber == 0) {  // 댓글인 경우
            groupNumber = getNewGroupNumber(recruitmentComment.getRecruitmentPost());
        }

        long groupOrder = getNewGroupOrder(recruitmentComment.getRecruitmentPost(), groupNumber);

        recruitmentComment.updateGroupNumberAndGroupOrder(groupNumber, groupOrder);

        return recruitmentCommentRepository.save(recruitmentComment);
    }

    private long getNewGroupNumber(RecruitmentPost recruitmentPost) {
        RecruitmentComment recruitmentComment = recruitmentCommentRepository.findFirstByRecruitmentPostOrderByGroupNumberDesc(
                recruitmentPost).orElse(null);

        if (recruitmentComment == null) {
            return 1;
        }

        return recruitmentComment.getGroupNumber() + 1;
    }

    private long getNewGroupOrder(RecruitmentPost recruitmentPost, long groupNumber) {
        RecruitmentComment recruitmentComment = recruitmentCommentRepository.findFirstByRecruitmentPostAndGroupNumberOrderByGroupOrder(
                recruitmentPost, groupNumber).orElse(null);

        if (recruitmentComment == null) {
            return 1;
        }

        return recruitmentComment.getGroupOrder() + 1;
    }

    private void validateComment(RecruitmentComment recruitmentComment) {
        long validGroupNumber = 0;

        if (!recruitmentComment.isParent()) {
            // 대댓글인 경우
            RecruitmentComment foundRecruitmentComment = recruitmentCommentRepository.findLatestGroupOrderOrElseThrow(
                    recruitmentComment.getRecruitmentPost(), recruitmentComment.getGroupNumber());
            validGroupNumber = foundRecruitmentComment.getGroupNumber();
        }

        if (validGroupNumber != recruitmentComment.getGroupNumber()) {
            throw new RecruitmentCommentException(INVALID_COMMENT);
        }
    }
}
