package synk.meeteam.global.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Scope {
    OFF_CAMPUS("교외"),
    ON_CAMPUS("교내");

    private final String name;
}
