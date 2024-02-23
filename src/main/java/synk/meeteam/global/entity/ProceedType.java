package synk.meeteam.global.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProceedType {
    ON_LINE("온라인"),
    OFF_LINE("오프라인"),
    ON_AND_OFFLINE("온/오프라인");

    private final String name;
}
