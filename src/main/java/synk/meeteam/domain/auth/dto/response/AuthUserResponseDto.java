package synk.meeteam.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

public class AuthUserResponseDto {

    @Getter
    public static abstract class InnerParent {
        @Schema(description = "인가 타입, LOGIN: 회원가입된 유저, SIGN_UP: 회원가입되지 않은 유저", example = "LOGIN or SIGN_UP")
        private AuthType authType;
        @Schema(description = "역할, User: 회원가입된 유저, GUEST: 회원가입되지 않은 유저", example = "USER or GUEST")
        private Authority authority;
        @Schema(description = "유저 대학교", example = "광운대학교")
        private String universityName;

        public InnerParent(AuthType authType, Authority authority, String universityName) {
            this.authType = authType;
            this.authority = authority;
            this.universityName = universityName;
        }
    }

    @Getter
    @ToString
    public static class login extends InnerParent {
        @Schema(description = "user Id", example = "4OaVE421DSwR63xfKf6vxA==")
        private String userId;
        @Schema(description = "유저 닉네임", example = "송민규짱짱맨")
        private String nickname;
        @Schema(description = "유저 프로필 사진", example = "url 형태")
        private String imageUrl;
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1N...(액세스 토큰)")
        private String accessToken;
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1N...(리프레시 토큰)")
        private String refreshToken;

        public login(AuthType authType, Authority authority, String userId, String nickname, String imageUrl,
                     String universityName, String accessToken, String refreshToken) {
            super(authType, authority, universityName);
            this.userId = userId;
            this.nickname = nickname;
            this.imageUrl = imageUrl;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    @Getter
    @ToString
    public static class create extends InnerParent {
        @Schema(description = "플랫폼 Id", example = "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM")
        private String platformId;

        public create(AuthType authType, Authority authority, String universityName, String platformId) {
            super(authType, authority, universityName);
            this.platformId = platformId;
        }
    }
}
