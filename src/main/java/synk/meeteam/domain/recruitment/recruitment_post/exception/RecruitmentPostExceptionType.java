package synk.meeteam.domain.recruitment.recruitment_post.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor
public enum RecruitmentPostExceptionType implements ExceptionType {
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "접근할 수 없는 유저입니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "유효하지 않은 게시글 id 입니다.");

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
