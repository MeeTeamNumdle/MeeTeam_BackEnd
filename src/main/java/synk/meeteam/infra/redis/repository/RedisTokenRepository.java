package synk.meeteam.infra.redis.repository;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.NOT_FOUND_REFRESH_TOKEN;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.security.jwt.service.vo.TokenVO;


@Repository
public interface RedisTokenRepository extends CrudRepository<TokenVO, String> {
    Optional<TokenVO> findByPlatformId(String memberId);

    default TokenVO findByPlatformIdOrElseThrowException(String memberId) {
        return findByPlatformId(memberId)
                .filter(tokenVO -> !tokenVO.isBlack())
                .orElseThrow(
                        () -> new AuthException(NOT_FOUND_REFRESH_TOKEN));
    }
}
