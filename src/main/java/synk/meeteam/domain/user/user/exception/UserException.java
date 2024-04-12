package synk.meeteam.domain.user.user.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class UserException extends BaseCustomException {
    public UserException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
