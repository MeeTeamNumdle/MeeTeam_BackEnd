package synk.meeteam.domain.user.user_link;

import java.util.List;
import synk.meeteam.domain.user.user_link.dto.UpdateUserLinkDto;
import synk.meeteam.domain.user.user_link.entity.UserLink;

public class UserLinkFixture {

    public static List<UserLink> createUserLinkFixture() {
        return List.of(
                new UserLink("링크1", "디스크립션1"),
                new UserLink("링크2", "디스크립션2")
        );
    }

    public static List<UpdateUserLinkDto> createUserLinkDtoFixture() {
        return List.of(
                new UpdateUserLinkDto("링크1", "디스크립션1"),
                new UpdateUserLinkDto("링크2", "디스크립션2")
        );
    }
}
