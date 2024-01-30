package synk.meeteam.domain.auth.service.vo;


import lombok.Builder;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.entity.enums.Authority;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

@Builder
public record UserSignUpVO(Long userId, String email, String name, PlatformType platformType, Authority authority,
                           String platformId, String phoneNumber, AuthType authType) {
    public static UserSignUpVO of(User user, PlatformType platformType, Authority authority, AuthType authType) {
        return UserSignUpVO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .platformType(platformType)
                .platformId(user.getPlatformId())
                .authority(authority)
                .phoneNumber(user.getPhoneNumber())
                .authType(authType)
                .build();
    }
}
