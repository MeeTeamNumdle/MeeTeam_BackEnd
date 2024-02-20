package synk.meeteam.domain.auth.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import synk.meeteam.domain.auth.dto.request.AuthUserRequestDto;
import synk.meeteam.domain.auth.dto.request.SignUpUserRequestDto;
import synk.meeteam.domain.auth.dto.request.VerifyUserRequestDto;
import synk.meeteam.domain.auth.dto.response.AuthUserResponseDto;
import synk.meeteam.domain.auth.dto.response.LogoutUserResponseDto;
import synk.meeteam.domain.auth.dto.response.ReissueUserResponseDto;
import synk.meeteam.domain.auth.dto.response.SignUpUserResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;


@Tag(name = "auth", description = "인증 관련 API")
public interface AuthApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "로그인에 성공하였습니다"),
                    @ApiResponse(responseCode = "201", description = "회원가입에 성공하였습니다"),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 플랫폼 인가코드입니다, 입력값이 올바르지 않습니다, 올바르지 않은 플랫폼 유형입니다")
            }
    )
    @Operation(summary = "소셜 로그인(1)")
    ResponseEntity<AuthUserResponseDto> login(
            @RequestHeader(value = "authorization-code") final String authorizationCode,
            @RequestBody @Valid final
            AuthUserRequestDto requestDto);


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "임시 유저 생성 및 이메일 전송에 성공하였습니다."),
            }
    )
    @Operation(summary = "임시 유저 생성 및 이메일 전송(2)")
    ResponseEntity<SignUpUserResponseDto> createTempUserAndSendEmail(
            @RequestBody @Valid SignUpUserRequestDto requestDto
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "이메일 인증 및 회원가입에 성공하였습니다."),
            }
    )
    @Operation(summary = "이메일 인증 및 회원가입(3)")
    ResponseEntity<AuthUserResponseDto> signUp(
            @RequestBody @Valid VerifyUserRequestDto requestDto);


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "리프레시 토큰 재발급에 성공하였습니다."),
                    @ApiResponse(responseCode = "400", description = "서비스에서 발급되지 않거나 이미 사용된 리프레시 토큰입니다."),
                    @ApiResponse(responseCode = "401", description = "기한이 만료된 리프레시 토큰입니다.")
            }
    )
    @Operation(summary = "액세스 토큰 & 리프레시 토큰 재발급", description = "액세스 토큰 및 리프레시 토큰을 재발급 받습니다.")
    ResponseEntity<ReissueUserResponseDto> reissue(HttpServletRequest request);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "로그아웃에 성공하였습니다."),
            }
    )
    @Operation(summary = "로그아웃")
    ResponseEntity<LogoutUserResponseDto> logout(@AuthUser final User user);
}
