package synk.meeteam.domain.portfolio.portfolio.exception;

import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

public enum PortfolioExceptionType implements ExceptionType {
    ;

    @Override
    public HttpStatus httpStatus() {
        return null;
    }

    @Override
    public String message() {
        return null;
    }
}
