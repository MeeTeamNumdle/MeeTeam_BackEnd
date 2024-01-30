package synk.meeteam.infra.redis.repository;

import static synk.meeteam.infra.redis.exception.RedisExceptionType.NOT_FOUND_EMAIL_CODE;
import static synk.meeteam.infra.redis.exception.RedisExceptionType.NOT_FOUND_TEMP_USER;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.user.entity.UserVO;
import synk.meeteam.infra.redis.exception.RedisException;

@Repository
public interface RedisUserRepository extends CrudRepository<UserVO, String> {
    Optional<UserVO> findByPlatformId(String platformId);

    default UserVO findByPlatformIdOrElseThrowException(String platformId) {
        return findByPlatformId(platformId).orElseThrow(() -> new RedisException(NOT_FOUND_TEMP_USER));
    }

    Optional<UserVO> findByEmailCode(String emailCode);

    default UserVO findByEmailCodeOrElseThrowException(String emailCode) {
        return findByEmailCode(emailCode).orElseThrow(() -> new RedisException(NOT_FOUND_EMAIL_CODE));
    }
}
