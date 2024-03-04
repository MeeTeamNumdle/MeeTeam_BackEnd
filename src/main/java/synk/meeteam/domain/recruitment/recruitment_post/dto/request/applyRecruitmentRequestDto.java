package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "applyRecruitmentRequestDto", description = "구인 신청 Dto")
public record applyRecruitmentRequestDto(
        @Schema(description = "구인글 id", example = "1")
        Long postId,
        @Schema(description = "신청 역할 id", example = "1")
        Long applicationRoleId,
        @Schema(description = "전하는 말", example = "저 관심있어용")
        String message
) {
}
