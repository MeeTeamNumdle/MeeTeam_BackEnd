package synk.meeteam.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

@Schema(name = "AuthUserResponseDto", description = "소셜 로그인 응답 Dto")
public record AuthUserResponseDto(
        @Schema(description = "user Id", example = "4OaVE421DSwR63xfKf6vxA==(200) or null(201)")
        String userId,
        @Schema(description = "플랫폼 Id", example = "null(200) or Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM(201)")
        String platformId,
        @NotNull
        @Schema(description = "인가 타입(LOGIN or SIGN_UP)", example = "LOGIN(200) or SIGN_UP(201)")
        AuthType authType,
        @NotNull
        @Schema(description = "유저 이름", example = "송민규")
        String userName,
        @NotNull
        @Schema(description = "유저 프로필 사진", example = "url 형태")
        String pictureUrl,
        @NotNull
        @Schema(description = "역할, GUEST는 임시 유저", example = "USER(200) or GUEST(201)")
        Authority authority,
        @Schema(description = "액세스 토큰", example = "null(201) or 액세스토큰 eyJhbGciOiJIUzI1N~~ (200)")
        String accessToken,
        @Schema(description = "리프레시 토큰", example = "null(201) or 리프레시 토큰 eyJhbGciOiJIUzI1N~~ (200)")
        String refreshToken) {
    public static AuthUserResponseDto of(String userId, String platformId, AuthType authType, String userName, String pictureUrl, Authority authority,
                                         String accessToken, String refreshToken) {
        return new AuthUserResponseDto(userId, platformId, authType, userName, pictureUrl, authority, accessToken, refreshToken);
    }
}
