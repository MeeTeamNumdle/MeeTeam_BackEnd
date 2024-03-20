package synk.meeteam.domain.user.user.repository;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.NOT_FOUND_USER;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.user.exception.UserException;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);

    default User findByIdOrElseThrow(Long userId) {
        return findById(userId).orElseThrow(() -> new UserException(NOT_FOUND_USER));
    }

    Optional<User> findByNickname(String nickname);

    Optional<User> findByPlatformIdAndPlatformType(String platformId, PlatformType platformType);

    Optional<User> findByPlatformId(String platformId);

    default User findByPlatformIdOrElseThrowException(String platformId) {
        return findByPlatformId(platformId).orElseThrow(() -> new AuthException(NOT_FOUND_USER));
    }

    default User findByPlatformIdAndPlatformTypeOrElseThrowException(String platformId, PlatformType platformType) {
        return findByPlatformIdAndPlatformType(platformId, platformType).orElseThrow(
                () -> new AuthException(NOT_FOUND_USER));
    }



}
