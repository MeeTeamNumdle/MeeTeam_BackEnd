package synk.meeteam.global.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LinkType {
    KAKAO("카카오톡"),
    DISCORD("디스코드"),
    NOTION("노션"),
    SLACK("슬랙");

    private final String name;
}
