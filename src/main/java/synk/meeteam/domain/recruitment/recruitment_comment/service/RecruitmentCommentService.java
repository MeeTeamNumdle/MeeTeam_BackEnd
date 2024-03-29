package synk.meeteam.domain.recruitment.recruitment_comment.service;

import static synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentExceptionType.INVALID_COMMENT;

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
    public RecruitmentComment registerRecruitmentComment(RecruitmentComment recruitmentComment) {
        validateComment(recruitmentComment);

        return recruitmentCommentRepository.save(recruitmentComment);
    }

    private void validateComment(RecruitmentComment recruitmentComment) {
        long nextGroupNumber = 1;
        long nextGroupOrder = 1;

        if (recruitmentComment.isParent()) {  // 페치 조인으로 변경하는게 좋을듯?
            // 댓글인 경우
            // 현재 가장 최신 그룹 번호를 찾아서, 그 다음 번호로 할당 + 그룹오더 1로 할당
            RecruitmentComment foundRecruitmentComment = recruitmentCommentRepository.findFirstByRecruitmentPostOrderByGroupNumberDesc(
                    recruitmentComment.getRecruitmentPost()).orElse(null);
            if (foundRecruitmentComment != null) {
                nextGroupNumber = foundRecruitmentComment.getGroupNumber() + 1;
            }

        } else if (!recruitmentComment.isParent()) {
            // 대댓글인 경우
            // 해당 그룹 번호가 있는지 찾아서, 그 그룹 오더 가장 최신 번호 찾아서, 그 다음 번호로 할당
            RecruitmentComment foundRecruitmentComment = recruitmentCommentRepository.findLatestGroupOrderOrElseThrow(
                    recruitmentComment.getRecruitmentPost(), recruitmentComment.getGroupNumber());

            nextGroupOrder = foundRecruitmentComment.getGroupOrder() + 1;
        }

        if (nextGroupNumber != recruitmentComment.getGroupNumber()
                || nextGroupOrder != recruitmentComment.getGroupOrder()) {
            throw new RecruitmentCommentException(INVALID_COMMENT);
        }
    }
}
