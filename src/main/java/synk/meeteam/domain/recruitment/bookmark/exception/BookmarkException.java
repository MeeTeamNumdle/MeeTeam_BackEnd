package synk.meeteam.domain.recruitment.bookmark.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class BookmarkException extends BaseCustomException {
    public BookmarkException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
