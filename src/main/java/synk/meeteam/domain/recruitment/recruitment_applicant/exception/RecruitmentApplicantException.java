package synk.meeteam.domain.recruitment.recruitment_applicant.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class RecruitmentApplicantException extends BaseCustomException {
    public RecruitmentApplicantException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
