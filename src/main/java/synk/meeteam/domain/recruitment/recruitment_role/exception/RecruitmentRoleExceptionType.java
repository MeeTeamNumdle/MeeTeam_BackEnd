package synk.meeteam.domain.recruitment.recruitment_role.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor
public enum RecruitmentRoleExceptionType implements ExceptionType {
    SS_601(HttpStatus.BAD_REQUEST, "SS-601"),
    INVALID_RECRUITMENT_ROLE_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 구인 역할 id 입니다.");

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
