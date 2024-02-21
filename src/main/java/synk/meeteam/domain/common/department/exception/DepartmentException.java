package synk.meeteam.domain.common.department.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class DepartmentException extends BaseCustomException {
    public DepartmentException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
