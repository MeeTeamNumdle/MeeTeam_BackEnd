package synk.meeteam.domain.university.exception;

import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

public enum UniversityExceptionType implements ExceptionType {
    ;

    @Override
    public HttpStatus httpStatus() {
        return null;
    }

    @Override
    public String message() {
        return null;
    }
}
