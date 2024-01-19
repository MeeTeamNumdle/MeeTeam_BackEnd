package synk.meeteam.domain.university.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniversityExceptionType implements ExceptionType {

    NOT_FOUND_EMAIL_REGEX(HttpStatus.NOT_FOUND, "유효한 학교 이메일이 아닙니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
