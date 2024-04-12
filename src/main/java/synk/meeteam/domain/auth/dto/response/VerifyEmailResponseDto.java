package synk.meeteam.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VerifyEmailResponseDto", description = "회원가입 응답 Dto")
public record VerifyEmailResponseDto(
        @Schema(description = "플랫폼 Id", example = "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM")
        String  platformId
        ) {
    public static VerifyEmailResponseDto of(String platformId) {
        return new VerifyEmailResponseDto(platformId);
    }
}
