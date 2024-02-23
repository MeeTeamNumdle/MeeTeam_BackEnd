package synk.meeteam.domain.auth.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import synk.meeteam.domain.auth.dto.request.AuthUserRequestDto;
import synk.meeteam.domain.auth.dto.request.SignUpUserRequestDto;
import synk.meeteam.domain.auth.dto.request.VerifyEmailRequestDto;
import synk.meeteam.domain.auth.dto.response.AuthUserResponseDto;
import synk.meeteam.domain.auth.dto.response.ReissueUserResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;


@Tag(name = "auth", description = "인증 관련 API")
@SecurityRequirements
public interface AuthApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "로그인에 성공하였습니다. authType: LOGIN, authority: USER", content = @Content(schema = @Schema(implementation = AuthUserResponseDto.login.class))),
                    @ApiResponse(responseCode = "201", description = "회원가입이 필요합니다. 회원가입을 위해 이메일 인증 단계로 넘어가야 합니다. authType: SIGN_UP, authority: GUEST", content = @Content(schema = @Schema(implementation = AuthUserResponseDto.create.class))),
                    @ApiResponse(responseCode = "400", description = "유효하지 않은 플랫폼 인가코드입니다, 입력값이 올바르지 않습니다, 올바르지 않은 플랫폼 유형입니다", content = @Content)
            }
    )
    @Operation(summary = "소셜 로그인", description = "authType: LOGIN(200) or SIGN_UP(201), authority: USER(200) or GUEST(201)")
    ResponseEntity<AuthUserResponseDto.InnerParent> login(
            @RequestBody @Valid final
            AuthUserRequestDto requestDto);


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "이메일 인증을 위한 이메일 전송에 성공하였습니다."),
            }
    )
    @Operation(summary = "이메일 인증을 위한 요청(해당 API 호출 후, 사용자 메일주소로 메일이 발송됨)")
    ResponseEntity<Void> requestEmailVerify(
            @RequestBody @Valid VerifyEmailRequestDto requestDto
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "이메일 인증 및 회원가입에 성공하였습니다. authType: SIGN_UP, authority: USER"),
            }
    )
    @Operation(summary = "이메일 인증 완료 및 회원가입", description = "authType: SIGN_UP, authority: USER")
    ResponseEntity<AuthUserResponseDto.login> signUp(
            @RequestBody @Valid SignUpUserRequestDto requestDto);


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "리프레시 토큰 재발급에 성공하였습니다."),
                    @ApiResponse(responseCode = "400", description = "서비스에서 발급되지 않거나 이미 사용된 리프레시 토큰입니다.", content = @Content),
                    @ApiResponse(responseCode = "401", description = "기한이 만료된 리프레시 토큰입니다.", content = @Content)
            }
    )
    @Operation(summary = "액세스 토큰 & 리프레시 토큰 재발급", description = "액세스 토큰 및 리프레시 토큰을 재발급 받습니다.")
    @SecurityRequirement(name = "Authorization-refresh")
    ResponseEntity<ReissueUserResponseDto> reissue(HttpServletRequest request);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "로그아웃에 성공하였습니다."),
            }
    )
    @Operation(summary = "로그아웃")
    @SecurityRequirement(name = "Authorization")
    ResponseEntity<Void> logout(@AuthUser final User user);
}
