package synk.meeteam.domain.recruitment.recruitment_comment.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor
public enum RecruitmentCommentExceptionType implements ExceptionType {
    INVALID_COMMENT(HttpStatus.BAD_REQUEST, "잘못된 댓글 등록 요청입니다."),
    INVALID_USER(HttpStatus.BAD_REQUEST, "해당 댓글의 수정 권한이 없습니다.");

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
