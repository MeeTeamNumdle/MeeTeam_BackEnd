package synk.meeteam.global.entity;

import static synk.meeteam.global.entity.exception.EnumExceptionType.INVALID_LINK_TYPE_NAME;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synk.meeteam.global.entity.exception.EnumException;

@RequiredArgsConstructor
@Getter
public enum LinkType {
    KAKAO("카카오톡"),
    DISCORD("디스코드"),
    NOTION("노션"),
    SLACK("슬랙");

    private final String name;

    public static LinkType findByName(String linkName) {
        return Arrays.stream(LinkType.values())
                .filter(link -> link.name.equals(linkName))
                .findAny()
                .orElseThrow(() -> new EnumException(INVALID_LINK_TYPE_NAME));
    }
}
