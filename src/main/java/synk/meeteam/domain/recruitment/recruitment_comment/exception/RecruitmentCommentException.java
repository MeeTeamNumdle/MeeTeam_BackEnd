package synk.meeteam.domain.recruitment.recruitment_comment.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class RecruitmentCommentException extends BaseCustomException {
    public RecruitmentCommentException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
