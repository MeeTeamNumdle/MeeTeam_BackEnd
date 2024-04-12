package synk.meeteam.infra.redis.exception;

import synk.meeteam.global.common.exception.BaseCustomException;
import synk.meeteam.global.common.exception.ExceptionType;

public class RedisException extends BaseCustomException {
    public RedisException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
