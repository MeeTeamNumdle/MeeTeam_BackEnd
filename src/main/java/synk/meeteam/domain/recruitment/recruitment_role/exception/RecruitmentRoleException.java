package synk.meeteam.domain.recruitment.recruitment_role.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class RecruitmentRoleException extends BaseCustomException {
    public RecruitmentRoleException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
