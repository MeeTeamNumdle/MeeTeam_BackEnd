package synk.meeteam.global.entity.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum EnumExceptionType implements ExceptionType {
    /**
     * 400 Bad Request
     */
    INVALID_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "올바르지 않는 유형 이름입니다."),
    INVALID_SCOPE_NAME(HttpStatus.BAD_REQUEST, "올바르지 않는 범위 이름입니다."),
    INVALID_LINK_TYPE_NAME(HttpStatus.BAD_REQUEST, "올바르지 않는 링크타입 이름입니다."),
    INVALID_PROCEED_TYPE_NAME(HttpStatus.BAD_REQUEST, "올바르지 않는 진행방식 이름입니다.");


    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }
}
