package synk.meeteam.domain.user.user_link.service;

import java.util.List;
import synk.meeteam.domain.user.user_link.dto.UpdateUserLinkDto;
import synk.meeteam.domain.user.user_link.entity.UserLink;

public interface UserLinkService {
    List<UserLink> changeUserLinks(Long id, List<UpdateUserLinkDto> userLinkDtos);

    List<UserLink> getUserLink(Long userId);
}
