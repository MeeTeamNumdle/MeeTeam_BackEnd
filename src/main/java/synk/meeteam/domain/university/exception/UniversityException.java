package synk.meeteam.domain.university.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class UniversityException extends BaseCustomException {
    public UniversityException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
