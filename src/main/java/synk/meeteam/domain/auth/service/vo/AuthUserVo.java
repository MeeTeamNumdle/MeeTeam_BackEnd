package synk.meeteam.domain.auth.service.vo;


import lombok.Builder;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

@Builder
public record AuthUserVo(Long userId, String email, String name, String pictureUrl, PlatformType platformType,
                         Authority authority, String platformId, String phoneNumber, AuthType authType) {
    public static AuthUserVo of(User user, PlatformType platformType, Authority authority, AuthType authType) {
        return AuthUserVo.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .pictureUrl(user.getPictureUrl())
                .platformType(platformType)
                .platformId(user.getPlatformId())
                .authority(authority)
                .phoneNumber(user.getPhoneNumber())
                .authType(authType)
                .build();
    }
}
