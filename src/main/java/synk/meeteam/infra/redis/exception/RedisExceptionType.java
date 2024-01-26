package synk.meeteam.infra.redis.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor
public enum RedisExceptionType implements ExceptionType {
    INVALID_VERIFY_MAIL(HttpStatus.BAD_REQUEST, "잘못된 이메일 코드 입니다."),
    NOT_FOUND_TEMP_USER(HttpStatus.NOT_FOUND, "해당 학사 인증을 요청한 유저를 찾을 수 없습니다."),
    NOT_FOUND_EMAIL_CODE(HttpStatus.NOT_FOUND, "해당 이메일 코드를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }
}
