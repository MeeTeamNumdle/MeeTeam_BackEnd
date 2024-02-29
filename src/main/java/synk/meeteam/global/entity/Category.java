package synk.meeteam.global.entity;

import static synk.meeteam.global.entity.exception.EnumExceptionType.INVALID_CATEGORY_NAME;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import synk.meeteam.global.entity.exception.EnumException;

@Getter
@RequiredArgsConstructor
public enum Category {
    PROJECT("프로젝트"),
    STUDY("스터디"),
    CLUB("동아리"),
    CONTEST("공모전"),
    ;

    private final String name;

    public static Category findByName(String categoryName) {
        return Arrays.stream(Category.values())
                .filter(category -> category.name.equals(categoryName))
                .findAny()
                .orElseThrow(() -> new EnumException(INVALID_CATEGORY_NAME));
    }
}
