package synk.meeteam.domain.user.sub_email.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateSubEmailDto", description = "서브 이메일 Dto")
public record CreateSubEmailDto(
        @Schema(description = "이메일", example = "goder0@naver.com")
        String email,
        @Schema(description = "공개여부", example = "true")
        boolean isPublic
) {
}
