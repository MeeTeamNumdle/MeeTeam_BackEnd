package synk.meeteam.domain.recruitment.recruitment.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class RecruitmentException extends BaseCustomException {
    public RecruitmentException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
