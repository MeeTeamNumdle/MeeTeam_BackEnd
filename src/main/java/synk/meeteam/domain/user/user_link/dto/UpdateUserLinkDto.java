package synk.meeteam.domain.user.user_link.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SaveUserLinkDto", description = "유저 링크 Dto")
public record UpdateUserLinkDto(
        @Schema(description = "url", example = "naver.com")
        String url,
        @Schema(description = "부연설명", example = "네이버")
        String description
) {

}
