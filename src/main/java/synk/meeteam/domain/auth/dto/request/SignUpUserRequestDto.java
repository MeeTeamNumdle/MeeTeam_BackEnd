package synk.meeteam.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "VerifyUserRequestDto", description = "이메일 인증 확인 및 회원가입 요청")
public record SignUpUserRequestDto(
        @NotNull
        @Schema(description = "이메일 코드", example = "0a2c1be3-5b99-47d1-bd02-6bd4754a7688")
        String emailCode,
        @NotNull
        @Schema(description = "닉네임", example = "송민규짱짱맨")
        String nickName

        ) {
}
