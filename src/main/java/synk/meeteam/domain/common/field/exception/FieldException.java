package synk.meeteam.domain.common.field.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class FieldException extends BaseCustomException {
    public FieldException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
