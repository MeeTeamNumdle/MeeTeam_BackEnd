package synk.meeteam.domain.user.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CheckDuplicateNicknameResponseDto", description = "닉네임 중복 확인 Dto")
public record CheckDuplicateNicknameResponseDto(
        @Schema(description = "해당 닉네임 이용 가능 여부, true: 사용가능 false: 사용불가능", example = "true")

        boolean isEnable
) {
    public static CheckDuplicateNicknameResponseDto of(boolean available) {
        return new CheckDuplicateNicknameResponseDto(available);
    }
}
