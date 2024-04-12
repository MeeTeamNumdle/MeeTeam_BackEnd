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
    SS_100(HttpStatus.BAD_REQUEST, "SS-100"),

    /**
     * 500
     */
    P_100(HttpStatus.INTERNAL_SERVER_ERROR, "P-100");


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
