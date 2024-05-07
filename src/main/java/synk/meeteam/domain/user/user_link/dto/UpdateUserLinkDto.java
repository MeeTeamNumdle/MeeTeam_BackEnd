package synk.meeteam.domain.user.user_link.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "SaveUserLinkDto", description = "유저 링크 Dto")
public record UpdateUserLinkDto(
        @Schema(description = "url", example = "naver.com")
        @Pattern(regexp = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#()?&/=]*)")
        String url,
        @Schema(description = "부연설명", example = "네이버")
        @Size(max = 20)
        String description
) {

}
