package synk.meeteam.domain.common.university.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniversityExceptionType implements ExceptionType {

    BAD_REQUEST_UNIVERSITY_ID(HttpStatus.BAD_REQUEST, "유효하지 않는 학교 id 입니다."),


    NOT_FOUND_EMAIL_REGEX(HttpStatus.NOT_FOUND, "유효한 학교 이메일이 아닙니다."),
    NOT_FOUND_UNIVERSITY_AND_DEPARTMENT(HttpStatus.NOT_FOUND, "유효한 학교명 및 학과명을 찾지 못했습니다.");


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
