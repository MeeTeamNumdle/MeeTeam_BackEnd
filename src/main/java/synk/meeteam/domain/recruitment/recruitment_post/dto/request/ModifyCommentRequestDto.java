package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "ModifyCommentRequestDto", description = "댓글 수정 요청 Dto")
public record ModifyCommentRequestDto(
        @Schema(description = "댓글 id", example = "2")
        @NotNull
        Long commentId,
        @Schema(description = "댓글 내용", example = "저 진짜 하고 싶어요")
        @NotNull
        @Size(min = 1, max = 100)
        String content
) {
}
