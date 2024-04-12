package synk.meeteam.domain.recruitment.recruitment_applicant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "SetLinkRequestDto", description = "오픈 카톡 링크 설정 Dto")
public record SetLinkRequestDto(
        @NotNull
        @Schema(description = "링크", example = "https://open.kakao.com/o/gLmqdijg")
        String link
) {
}
