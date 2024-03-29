package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "CreateCommentRequestDto", description = "댓글 등록 요청 Dto")
public record CreateCommentRequestDto(

        @Schema(description = "댓글 내용", example = "나 진짜 관심있어여")
        @NotNull
        @Size(min = 1, max = 100)
        String content,
        @Schema(description = "댓글/대댓글 여부, 대댓글이면 false", example = "true")
        @NotNull
        boolean isParent,
        @Schema(description = "댓글 그룹 번호", example = "2")
        long groupNumber,
        @Schema(description = "특정 댓글 그룹 내 순서", example = "3")
        long groupOrder

) {
}
