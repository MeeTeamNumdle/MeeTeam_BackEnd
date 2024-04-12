package synk.meeteam.domain.common.role.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class RoleException extends BaseCustomException {
    public RoleException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
