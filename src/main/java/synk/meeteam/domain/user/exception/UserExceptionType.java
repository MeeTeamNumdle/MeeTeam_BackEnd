package synk.meeteam.domain.user.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor
public enum UserExceptionType implements ExceptionType {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유효한 유저를 찾지 못했습니다.");

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
