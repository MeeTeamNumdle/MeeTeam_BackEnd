package synk.meeteam.global.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String LOG_FORMAT = "%s";
    private static final String SERVER_ERROR_MESSAGE = "서버 오류가 발생하였습니다.";

    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<ExceptionResponse> handleBaseException(BaseCustomException e) {
        ExceptionType exceptionType = e.getExceptionType();
        log.info(String.format(LOG_FORMAT, e.getMessage()), e);
        return ResponseEntity.status(exceptionType.httpStatus())
                .body(ExceptionResponse.of(exceptionType.name(), exceptionType.message()));
    }
}
