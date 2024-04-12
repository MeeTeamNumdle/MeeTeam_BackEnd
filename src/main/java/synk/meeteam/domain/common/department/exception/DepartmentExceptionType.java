package synk.meeteam.domain.common.department.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DepartmentExceptionType implements ExceptionType {

    NOT_FOUND_DEPARTMENT_ID(HttpStatus.NOT_FOUND, "해당 학과 Id를 찾을 수 없습니다.");


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
