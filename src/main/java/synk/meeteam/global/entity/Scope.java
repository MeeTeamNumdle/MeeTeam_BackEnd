package synk.meeteam.global.entity;


import static synk.meeteam.global.entity.exception.EnumExceptionType.INVALID_SCOPE_NAME;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synk.meeteam.global.entity.exception.EnumException;

@RequiredArgsConstructor
@Getter
public enum Scope {
    OFF_CAMPUS("교외"),
    ON_CAMPUS("교내");

    private final String name;

    public static Scope findByName(String scopeName) {
        return Arrays.stream(Scope.values())
                .filter(scope -> scope.name.equals(scopeName))
                .findAny()
                .orElseThrow(() -> new EnumException(INVALID_SCOPE_NAME));
    }
}
