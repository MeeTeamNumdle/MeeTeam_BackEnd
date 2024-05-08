package synk.meeteam.infra.redis.repository;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.NOT_FOUND_REFRESH_TOKEN;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.security.jwt.service.vo.TokenVO;


@Repository
public interface RedisTokenRepository extends CrudRepository<TokenVO, String> {
    default TokenVO findByIdOrElseThrow(String userId) {
        return findById(userId)
                .filter(tokenVO -> !tokenVO.isBlack())
                .orElseThrow(() -> new AuthException(NOT_FOUND_REFRESH_TOKEN));
    }
}
