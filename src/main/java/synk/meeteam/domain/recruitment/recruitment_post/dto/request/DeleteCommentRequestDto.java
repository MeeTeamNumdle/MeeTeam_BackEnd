package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DeleteCommentRequestDto", description = "댓글 삭제 요청 Dto")
public record DeleteCommentRequestDto(
        @Schema(description = "댓글 그룹 번호", example = "2")
        long groupNumber,
        @Schema(description = "특정 댓글 그룹 내 순서", example = "3")
        long groupOrder
) {
}
