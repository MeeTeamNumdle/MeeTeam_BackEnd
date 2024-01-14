package synk.meeteam.domain.auth.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class AuthException extends BaseCustomException {
    public AuthException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
