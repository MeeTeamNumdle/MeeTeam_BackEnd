package synk.meeteam.domain.recruitment.recruitment_post.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class RecruitmentPostException extends BaseCustomException {
    public RecruitmentPostException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
