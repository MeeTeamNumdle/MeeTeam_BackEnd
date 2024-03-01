package synk.meeteam.domain.recruitment.recruitment_tag.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RecruitmentTagExceptionType implements ExceptionType {
    TAG_NAME_EXCEED_LIMIT(HttpStatus.BAD_REQUEST, "태그 글자수 제한을 초과했습니다.");

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
