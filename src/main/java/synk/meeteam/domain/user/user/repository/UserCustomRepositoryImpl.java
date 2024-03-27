package synk.meeteam.domain.user.user.repository;

import static synk.meeteam.domain.auth.exception.AuthExceptionType.NOT_FOUND_USER;
import static synk.meeteam.domain.user.user.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.exception.UserException;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public User findByIdFetchRole(Long id) {
        Optional<User> findUser = Optional.ofNullable(queryFactory
                .selectFrom(user)
                .leftJoin(user.interestRole).fetchJoin()
                .leftJoin(user.university).fetchJoin()
                .leftJoin(user.department).fetchJoin()
                .fetchOne());
        findUser.orElseThrow(() -> new UserException(NOT_FOUND_USER));
        return findUser.get();
    }
}
