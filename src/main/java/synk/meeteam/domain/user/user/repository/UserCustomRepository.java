package synk.meeteam.domain.user.user.repository;

import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.exception.UserException;

public interface UserCustomRepository {
    User findByIdFetchRole(Long id) throws UserException;
}
