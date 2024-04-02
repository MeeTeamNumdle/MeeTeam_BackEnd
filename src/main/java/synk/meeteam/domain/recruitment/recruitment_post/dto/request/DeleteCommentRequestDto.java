package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DeleteCommentRequestDto", description = "댓글 삭제 요청 Dto")
public record DeleteCommentRequestDto(
        @Schema(description = "댓글 id", example = "2")
        long commentId
) {
}
