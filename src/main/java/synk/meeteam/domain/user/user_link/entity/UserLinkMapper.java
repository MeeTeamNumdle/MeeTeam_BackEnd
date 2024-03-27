package synk.meeteam.domain.user.user_link.entity;

import org.mapstruct.Mapper;
import synk.meeteam.domain.user.user_link.dto.GetProfileUserLinkDto;
import synk.meeteam.domain.user.user_link.dto.UpdateUserLinkDto;

@Mapper(componentModel = "spring")
public interface UserLinkMapper {

    UserLink toUserLink(UpdateUserLinkDto updateUserLinkDto);

    GetProfileUserLinkDto toGetProfileUserLinkDto(UserLink userLink);

}
