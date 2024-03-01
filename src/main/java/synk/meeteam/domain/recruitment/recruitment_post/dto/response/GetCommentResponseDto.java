package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "GetCommentResponseDto", description = "구인글 조회 Dto")
public record GetCommentResponseDto(
        @Schema(description = "댓글 id", example = "1")
        Long id,
        @Schema(description = "댓글 작성자 닉네임", example = "song123")
        String nickname,
        @Schema(description = "댓글 작성자 프로필 사진", example = "url형태")
        String profileImg,
        @Schema(description = "댓글 내용", example = "저 관심있는데요..")
        String content,
        @Schema(description = "작성날짜", example = "2023-12-24")
        String createAt,
        @Schema(description = "구인글 작성자 여부", example = "true")
        boolean isWriter,
        @Schema(description = "대댓글", example = "")
        List<GetReplyResponseDto> replies
) {
}
