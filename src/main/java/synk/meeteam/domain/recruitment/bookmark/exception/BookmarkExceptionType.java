package synk.meeteam.domain.recruitment.bookmark.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import synk.meeteam.global.common.exception.ExceptionType;

@RequiredArgsConstructor
public enum BookmarkExceptionType implements ExceptionType {
    INVALID_BOOKMARK(HttpStatus.BAD_REQUEST, "이미 북마크된 구인글입니다.");

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
