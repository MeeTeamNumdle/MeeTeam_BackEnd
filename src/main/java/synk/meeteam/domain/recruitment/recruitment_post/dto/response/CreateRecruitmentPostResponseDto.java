package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateRecruitmentPostResponseDto(
        @Schema(description = "게시글 id", example = "1")
        Long recruitmentPostId
) {
}
