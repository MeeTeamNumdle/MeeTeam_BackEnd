package synk.meeteam.global.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    DEVELOP("개발");

    private final String name;

}
