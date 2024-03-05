package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetRecruitmentApplyInfoRequestDto", description = "구인 신청 정보 조회 Dto")
public record GetApplyInfoRequestDto(
        @Schema(description = "구인글 id", example = "1")
        Long postId
) {
}
