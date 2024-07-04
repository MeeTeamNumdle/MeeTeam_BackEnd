package synk.meeteam.domain.portfolio.portfolio.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PortfolioExceptionType implements ExceptionType {
    SS_110(HttpStatus.BAD_REQUEST, "SS-110"),
    NOT_FOUND_PORTFOLIO(HttpStatus.NOT_FOUND, "포트폴리오를 찾을 수 없습니다."),
    OVER_MAX_PIN_SIZE(HttpStatus.BAD_REQUEST, "포트폴리오의 최대 핀 개수를 초과합니다."),
    NOT_YOUR_PORTFOLIO(HttpStatus.FORBIDDEN, "본인의 포트폴리오가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String message() {
        return message;
    }
}
