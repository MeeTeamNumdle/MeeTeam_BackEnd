package synk.meeteam.infra.redis.repository;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_VERIFY_MAIL;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.infra.mail.MailVO;

public interface RedisVerifyRepository extends CrudRepository<MailVO, String> {
    Optional<MailVO> findByEmailCode(String emailCode);

    default MailVO findByEmailCodeOrElseThrowException(String emailCode) {
        return findByEmailCode(emailCode).orElseThrow(() -> new AuthException(INVALID_VERIFY_MAIL));
    }
}
