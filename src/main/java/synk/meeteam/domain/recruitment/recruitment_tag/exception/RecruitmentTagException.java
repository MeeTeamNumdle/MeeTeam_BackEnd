package synk.meeteam.domain.recruitment.recruitment_tag.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class RecruitmentTagException extends BaseCustomException {
    public RecruitmentTagException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
