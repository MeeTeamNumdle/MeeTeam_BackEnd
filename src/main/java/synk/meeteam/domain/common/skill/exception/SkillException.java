package synk.meeteam.domain.common.skill.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class SkillException extends BaseCustomException {
    public SkillException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
