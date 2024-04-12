package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SetOpenKakaoLinkRequestDto(
        @Schema(description = "링크", defaultValue = "https://open.kakao.com/o/gxlv96hg")
        @NotBlank
        @Pattern(regexp = "^https://open\\.kakao\\.com/", message = "오픈카톡방 링크가 잘못되었습니다.")
        String link
) {
}
