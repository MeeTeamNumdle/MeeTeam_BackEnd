package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "ApplyRecruitmentRequestDto", description = "구인 신청 Dto")
public record ApplyRecruitmentRequestDto(
        @NotNull
        @Schema(description = "신청하는 역할 id", example = "1")
        Long applyRoleId,
        @NotNull
        @Schema(description = "전하는 말", example = "저 관심있어용")
        String message
) {
}
