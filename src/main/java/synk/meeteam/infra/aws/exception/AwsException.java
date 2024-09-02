package synk.meeteam.infra.aws.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class AwsException extends BaseCustomException {
    public AwsException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
