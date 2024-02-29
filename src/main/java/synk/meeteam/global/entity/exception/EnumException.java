package synk.meeteam.global.entity.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class EnumException extends BaseCustomException {
    public EnumException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
