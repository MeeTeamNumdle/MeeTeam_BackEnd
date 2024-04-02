package synk.meeteam.domain.recruitment.bookmark.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor
public enum BookmarkExceptionType implements ExceptionType {
    INVALID_BOOKMARK(HttpStatus.BAD_REQUEST, "올바르지 않은 북마크 요청입니다.");

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
