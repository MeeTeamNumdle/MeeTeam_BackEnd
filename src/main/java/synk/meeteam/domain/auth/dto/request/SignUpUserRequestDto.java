package synk.meeteam.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;

@Schema(name = "SignUpUserRequestDto", description = "임시 회원 가입 및 이메일 인증 요청 Dto")
public record SignUpUserRequestDto(

        @NotNull
        @Schema(description = "플랫폼 Id", example = "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM")
        String platformId,
        @NotNull
        @Schema(description = "플랫폼 타입", example = "NAVER")
        PlatformType platformType,
        @NotNull
        @Schema(description = "학사 이메일 계정", example = "thdalsrb123")
        String emailId,
        @NotNull
        @Schema(description = "학교 id", example = "광운대학교")
        Long universityId,
        @NotNull
        @Schema(description = "학과 id", example = "소프트웨어학부")
        Long departmentId,
        @NotNull
        @Schema(description = "입학년도", example = "2018")
        int admissionYear
) {
}
