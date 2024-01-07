package synk.meeteam.domain.evaluation.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class EvaluationException extends BaseCustomException {
    public EvaluationException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
