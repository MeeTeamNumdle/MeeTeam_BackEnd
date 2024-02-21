package synk.meeteam.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LogoutUserResponseDto", description = "로그아웃 요청 Dto")
public record LogoutUserResponseDto(
        @Schema(description = "플랫폼 Id", example = "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM")
        String PlatformId
) {
    public static LogoutUserResponseDto of(String platformId) {
        return new LogoutUserResponseDto(platformId);
    }
}
