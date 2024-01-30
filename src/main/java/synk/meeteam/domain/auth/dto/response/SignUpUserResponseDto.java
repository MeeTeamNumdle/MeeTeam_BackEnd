package synk.meeteam.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "SignUpUserResponseDto", description = "회원가입 응답 Dto")
public record SignUpUserResponseDto(
        @NotNull
        @Schema(description = "플랫폼 Id", example = "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM")
        String  platformId
        ) {
    public static SignUpUserResponseDto of(String platformId) {
        return new SignUpUserResponseDto(platformId);
    }
}
