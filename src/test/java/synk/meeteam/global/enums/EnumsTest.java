package synk.meeteam.global.enums;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.LinkType;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;
import synk.meeteam.global.entity.exception.EnumException;

public class EnumsTest {

    @ParameterizedTest
    @ValueSource(strings = {"프로젝트", "스터디", "동아리", "공모전"})
    public void 입력에맞는Category찾기_입력에맞는Enum반환_여러입력(String categoryName) {
        Category category = Category.findByName(categoryName);
        Assertions.assertThat(category.getName()).isEqualTo(categoryName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"온라인", "오프라인", "상관없음"})
    public void 입력에맞는ProceedType찾기_입력에맞는Enum반환_여러입력(String proceedTypeName) {
        ProceedType proceedType = ProceedType.findByName(proceedTypeName);
        Assertions.assertThat(proceedType.getName()).isEqualTo(proceedTypeName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"교외", "교내"})
    public void 입력에맞는Scope찾기_입력에맞는Enum반환_여러입력(String scopeName) {
        Scope scope = Scope.findByName(scopeName);
        Assertions.assertThat(scope.getName()).isEqualTo(scopeName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"카카오톡", "디스코드", "슬랙", "노션"})
    public void 입력에맞는LinkType찾기_입력에맞는Enum반환_여러입력(String linkName) {
        LinkType linkType = LinkType.findByName(linkName);
        Assertions.assertThat(linkType.getName()).isEqualTo(linkName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"교내교외", "이건교내일까아닐까", " "})
    public void 입력에맞는Scope찾기_예외발생_유효하지않은입력(String scopeName) {
        Assertions.assertThatThrownBy(() -> Scope.findByName(scopeName))
                .isInstanceOf(EnumException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"프로작트", "STUDY", "CLUB", "공모"})
    public void 입력에맞는Category찾기_예외발생_유효하지않은입력(String categoryName) {
        Assertions.assertThatThrownBy(() -> Category.findByName(categoryName))
                .isInstanceOf(EnumException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"온라인.", "오프라인1", "온|오프라인", "온오프라인", "온_오프라인"})
    public void 입력에맞는ProceedType찾기_예외발생_유효하지않은입력(String proceedTypeName) {
        Assertions.assertThatThrownBy(() -> ProceedType.findByName(proceedTypeName))
                .isInstanceOf(EnumException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"KAKAO", "DISCORD", "슬랙 ", "노셔"})
    public void 입력에맞는LinkType찾기_예외발생_유효하지않은입력(String linkName) {
        Assertions.assertThatThrownBy(() -> LinkType.findByName(linkName))
                .isInstanceOf(EnumException.class);
    }
}
