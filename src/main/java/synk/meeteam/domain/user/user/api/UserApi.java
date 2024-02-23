package synk.meeteam.domain.user.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;
import synk.meeteam.domain.user.user.dto.response.CheckDuplicateNicknameResponseDto;

@Tag(name = "user", description = "유저 관련 API")
public interface UserApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "중복 검사 후 해당 닉네임 사용가능 여부 응답, true:사용가능(중복되지 않음), false:사용불가능(중복됨)")
            }
    )
    @Operation(summary = "닉네임 중복 확인 API")
    @SecurityRequirements
    CheckDuplicateNicknameResponseDto checkDuplicateNickname(@RequestParam String nickname);
}
