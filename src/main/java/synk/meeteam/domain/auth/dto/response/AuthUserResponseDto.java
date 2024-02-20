package synk.meeteam.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

@Schema(name = "AuthUserResponseDto", description = "소셜 로그인 응답 Dto")
public record AuthUserResponseDto(
        @NotNull
        @Schema(description = "플랫폼 Id", example = "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM")
        String platformId,
        @NotNull
        @Schema(description = "인가 타입(LOGIN or SIGN_UP)", example = "LOGIN")
        AuthType authType,
        @NotNull
        @Schema(description = "유저 이름", example = "송민규")
        String userName,
        @NotNull
        @Schema(description = "역할, GUEST는 임시 유저", example = "USER")
        Authority authority,
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInBsYXRmb3JtSWQiOiJEaTdsQ2hNR3hqWlZUYWk2ZDc2SG8xWUxEVV94TDh0bDFDZmRQTVY1U1FNIiwicGxhdGZvcm1UeXBlIjoiTkFWRVIiLCJpYXQiOjE3MDYyODA1MjMsImV4cCI6MTgxNDI4MDUyM30.doPtAdLQMZ8NeuhRAOg7GNMBBtFZzPOOZp60HskGtZ0")
        String accessToken,
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSZWZyZXNoVG9rZW4iLCJpYXQiOjE3MDYyODA1MjMsImV4cCI6MTcwNjg4NTMyM30.yvftTGVld0ZMnv1a79wpuzuTo8EJ1zOHoSlT_jfH3cs")
        String refreshToken) {
    public static AuthUserResponseDto of(String platformId, AuthType authType, String userName, Authority authority,
                                         String accessToken, String refreshToken) {
        return new AuthUserResponseDto(platformId, authType, userName, authority, accessToken, refreshToken);
    }
}
