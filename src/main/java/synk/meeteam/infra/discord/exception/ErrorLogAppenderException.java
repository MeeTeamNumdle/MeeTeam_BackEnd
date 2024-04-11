package synk.meeteam.infra.discord.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class ErrorLogAppenderException extends BaseCustomException {
    public ErrorLogAppenderException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
