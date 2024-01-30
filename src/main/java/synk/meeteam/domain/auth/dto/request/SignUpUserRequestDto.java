package synk.meeteam.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.entity.enums.PlatformType;

@Schema(name = "SignUpUserRequestDto", description = "임시 회원 가입 및 이메일 인증 요청 Dto")
public record SignUpUserRequestDto(

        @NotNull
        @Schema(description = "플랫폼 Id", example = "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM")
        String platformId,
        @NotNull
        @Schema(description = "플랫폼 타입", example = "NAVER")
        PlatformType platformType,
        @NotNull
        @Schema(description = "학사 이메일", example = "thdalsrb123@kw.ac.kr")
        String email,
        @NotNull
        @Schema(description = "학교 이름", example = "광운대학교")
        String universityName,
        @NotNull
        @Schema(description = "학과 이름", example = "소프트웨어학부")
        String departmentName,
        @NotNull
        @Schema(description = "입학년도", example = "2018")
        int admissionYear

) {
}
