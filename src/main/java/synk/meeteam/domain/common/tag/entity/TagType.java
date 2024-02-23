package synk.meeteam.domain.common.tag.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TagType {
    COURSE("강의명"),
    PROFESSOR("교수명"),
    MEETEAM("밋팀");

    private final String name;
}
