package synk.meeteam.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;

@Schema(name = "AuthUserRequestDto", description = "소셜 로그인 요청 Dto")
public record AuthUserRequestDto(
        @NotNull
        @Schema(description = "소셜 플랫폼 타입", example = "NAVER")
        PlatformType platformType) {

}