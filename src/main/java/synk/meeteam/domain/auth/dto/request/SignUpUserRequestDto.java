package synk.meeteam.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "SignUpUserRequestDto", description = "이메일 인증 확인 및 회원가입 요청 Dto")
public record SignUpUserRequestDto(
        @NotNull
        @Schema(description = "이메일 코드", example = "0a2c1be3-5b99-47d1-bd02-6bd4754a7688")
        String emailCode,
        @NotBlank(message = "닉네임은 null이거나 공백일 수 없습니다.")
        @Size(min = 4, max = 16, message = "닉네임은 4-16자로 제한됩니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣._]*$", message = "닉네임은 영어, 숫자, 한글, '_', '.' 문자만 가능합니다.")
        @Schema(description = "닉네임", example = "송민규짱짱맨")
        String nickName

        ) {
}
