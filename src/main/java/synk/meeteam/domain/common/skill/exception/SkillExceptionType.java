package synk.meeteam.domain.common.skill.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SkillExceptionType implements ExceptionType {
    /**
     * 400 Bad Request
     */
    INVALID_SKILL_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 스킬 id입니다.");


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
