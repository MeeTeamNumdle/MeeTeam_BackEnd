package synk.meeteam.domain.recruitment.recruitment_comment.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
                    S3FileName.getProfileImgUrl(Encryption.encryptLong(comment.getUserId())),
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
}
