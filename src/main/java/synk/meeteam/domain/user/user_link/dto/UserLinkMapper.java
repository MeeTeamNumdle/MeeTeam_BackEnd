package synk.meeteam.domain.user.user_link.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user_link.entity.UserLink;

@Mapper(componentModel = "spring")
public interface UserLinkMapper {

    @Mapping(source = "user", target = "user")
    UserLink toUserLink(User user, UpdateUserLinkDto updateUserLinkDto);

}
