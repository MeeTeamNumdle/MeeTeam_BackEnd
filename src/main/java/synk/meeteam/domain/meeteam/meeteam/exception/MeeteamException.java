package synk.meeteam.domain.meeteam.meeteam.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class MeeteamException extends BaseCustomException {
    public MeeteamException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
