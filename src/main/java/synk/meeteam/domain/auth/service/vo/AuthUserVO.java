package synk.meeteam.domain.auth.service.vo;


import lombok.Builder;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

@Builder
public record AuthUserVO(Long userId, String email, String universityName, String nickname, String profileImgUrl, PlatformType platformType,
                         Authority authority, String platformId, String phoneNumber, AuthType authType) {
    public static AuthUserVO of(User user, PlatformType platformType, Authority authority, AuthType authType) {
        return AuthUserVO.builder()
                .userId(user.getId())
                .email(user.getUniversityEmail())
                .universityName(user.getUniversity() == null ? null : user.getUniversity().getName())
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImgFileName())
                .platformType(platformType)
                .platformId(user.getPlatformId())
                .authority(authority)
                .phoneNumber(user.getPhoneNumber())
                .authType(authType)
                .build();
    }
}
