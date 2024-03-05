package synk.meeteam.domain.recruitment.recruitment_comment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.recruitment.recruitment_comment.repository.RecruitmentCommentRepository;
import synk.meeteam.domain.recruitment.recruitment_comment.service.vo.RecruitmentCommentVO;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetCommentResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetReplyResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;

@Service
@RequiredArgsConstructor
public class RecruitmentCommentService {
    private final RecruitmentCommentRepository recruitmentCommentRepository;

    // 가공된 형태를 많이 사용할 것 같다.
    // 그래서 Dto를 바로 반환하는 식으로 만들었다.
    public List<GetCommentResponseDto> getRecruitmentComments(RecruitmentPost recruitmentPost) {
        List<RecruitmentCommentVO> commentVOS = recruitmentCommentRepository.findAllByRecruitmentId(
                recruitmentPost.getId());

        // 그룹 번호로 매핑(key:group id)
        HashMap<Long, GetCommentResponseDto> groupedComments = groupComments(recruitmentPost,
                commentVOS);

        return groupedComments.entrySet().stream()
                .sorted(Entry.comparingByKey())
                .map(Entry::getValue)
                .collect(Collectors.toList());
    }

    private HashMap<Long, GetCommentResponseDto> groupComments(RecruitmentPost recruitmentPost,
                                                               List<RecruitmentCommentVO> commentVOS) {
        HashMap<Long, GetCommentResponseDto> CommentResponseDtos = new HashMap<>();
        Long writerId = recruitmentPost.getCreatedBy();

        for (RecruitmentCommentVO comment : commentVOS) {
            if (comment.isDeleted()) {
                continue;
            }

            boolean isWriter = writerId.equals(comment.getUserId());

            if (comment.isParent()) {
                List<GetReplyResponseDto> replies = new ArrayList<>();
                CommentResponseDtos.put(comment.getGroupNumber(),
                        new GetCommentResponseDto(comment.getId(), comment.getNickname(), comment.getProfileImg(),
                                comment.getContent(), comment.getCreateAt(), isWriter, replies));
                continue;
            }

            CommentResponseDtos.get(comment.getGroupNumber()).replies().add(new GetReplyResponseDto(comment.getId(),
                    comment.getNickname(), comment.getProfileImg(), comment.getContent(), comment.getCreateAt(),
                    isWriter));
        }

        return CommentResponseDtos;
    }


}
