package synk.meeteam.domain.user.exception;

import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

public class UserExceptionType implements ExceptionType {
    @Override
    public String name() {
        return null;
    }

    @Override
    public HttpStatus httpStatus() {
        return null;
    }

    @Override
    public String message() {
        return null;
    }
}
