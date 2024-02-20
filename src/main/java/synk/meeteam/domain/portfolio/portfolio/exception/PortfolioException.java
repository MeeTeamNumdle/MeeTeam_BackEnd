package synk.meeteam.domain.portfolio.portfolio.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class PortfolioException extends BaseCustomException {
    public PortfolioException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
