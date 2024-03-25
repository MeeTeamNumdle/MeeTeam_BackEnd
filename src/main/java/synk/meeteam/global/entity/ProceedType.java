package synk.meeteam.global.entity;

import static synk.meeteam.global.entity.exception.EnumExceptionType.INVALID_PROCEED_TYPE_NAME;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synk.meeteam.global.entity.exception.EnumException;

@RequiredArgsConstructor
@Getter
public enum ProceedType {
    ON_LINE("온라인"),
    OFF_LINE("오프라인"),
    NO_MATTER("상관없음");

    private final String name;

    public static ProceedType findByName(String proceedTypeName) {
        return Arrays.stream(ProceedType.values())
                .filter(proceedType -> proceedType.name.equals(proceedTypeName))
                .findAny()
                .orElseThrow(() -> new EnumException(INVALID_PROCEED_TYPE_NAME));
    }
}
