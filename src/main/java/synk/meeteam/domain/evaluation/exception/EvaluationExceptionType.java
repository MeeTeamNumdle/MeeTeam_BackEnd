package synk.meeteam.domain.evaluation.exception;

import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

public enum EvaluationExceptionType implements ExceptionType {
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
