package synk.meeteam.domain.auth.service.vo;


import lombok.Builder;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.domain.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.entity.enums.Role;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

@Builder
public record UserSignUpVO(Long userId, String email, String name, PlatformType platformType, Role role,
                           String platformId, String phoneNumber, AuthType authType) {
    public static UserSignUpVO of(User user, PlatformType platformType, Role role, AuthType authType) {
        return UserSignUpVO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .platformType(platformType)
                .platformId(user.getPlatformId())
                .role(role)
                .phoneNumber(user.getPhoneNumber())
                .authType(authType)
                .build();
    }
}
