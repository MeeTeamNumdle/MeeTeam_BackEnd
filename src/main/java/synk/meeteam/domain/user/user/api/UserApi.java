package synk.meeteam.domain.user.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import synk.meeteam.domain.user.user.dto.request.UpdateProfileRequestDto;
import synk.meeteam.domain.user.user.dto.response.CheckDuplicateNicknameResponseDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.common.exception.ExceptionResponse;
import synk.meeteam.security.AuthUser;

@Tag(name = "user", description = "유저 관련 API")
public interface UserApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "프로필 정보 업데이트에 성공하였습니다."),
                    @ApiResponse(responseCode = "400", description = "요청하신 정보가 올바르지 않습니다."),
            }
    )
    @Operation(summary = "유저 프로필 저장 API")
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<String> editProfile(@AuthUser User user, @RequestBody UpdateProfileRequestDto requestDto);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "프로필 조회에 성공하였습니다.",
                            content = @Content(schema = @Schema(implementation = GetProfileResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 유저입니다.",
                            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            }
    )
    @Operation(summary = "유저 프로필 조회 API")
    ResponseEntity<GetProfileResponseDto> getProfile(String userId);


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "중복 검사 후 해당 닉네임 사용가능 여부 응답, true:사용가능(중복되지 않음), false:사용불가능(중복됨)")
            }
    )
    @Operation(summary = "닉네임 중복 확인 API")
    @SecurityRequirements
    ResponseEntity<CheckDuplicateNicknameResponseDto> checkDuplicateNickname(@RequestParam String nickname);
}
