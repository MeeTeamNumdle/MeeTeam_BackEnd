package synk.meeteam.global.common.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    // 반환 형태 수정 필요
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> dtoValidation(final MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error)-> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }


}

