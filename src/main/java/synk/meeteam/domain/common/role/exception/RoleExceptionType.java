package synk.meeteam.domain.common.role.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleExceptionType implements ExceptionType {
    /**
     * 400 Bad Request
     */
    INVALID_ROLE_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 역할 id입니다.");


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
