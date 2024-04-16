package synk.meeteam.domain.auth.dto.response;

import org.mapstruct.Mapper;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.infra.oauth.service.vo.enums.AuthType;

@Mapper(componentModel = "spring")
public interface AuthUserResponseMapper {
    AuthUserResponseDto.create ofCreate(AuthType authType, Authority authority, String universityName, String platformId);

    AuthUserResponseDto.login ofLogin(AuthType authType, Authority authority, String userId, String nickname,
                                      String imageUrl, String universityName, String accessToken,
                                      String refreshToken);

}
