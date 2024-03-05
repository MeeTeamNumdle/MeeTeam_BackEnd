package synk.meeteam.global.common.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum GlobalExceptionType implements ExceptionType {
    /**
     * 400
     */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값에 유효하지 않은 입력 값이 있습니다."),

    /**
     * 500
     */
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");


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
