package synk.meeteam.domain.common.role.repository;

import static synk.meeteam.domain.common.role.entity.QRole.role;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.common.role.dto.QRoleDto;
import synk.meeteam.domain.common.role.dto.RoleDto;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RoleDto> findAllByKeywordAndTopLimit(String keyword, long limit) {
        return queryFactory
                .select(new QRoleDto(role.id, role.name))
                .from(role)
                .where(role.name.startsWith(keyword))
                .limit(limit)
                .orderBy(role.name.length().asc().nullsLast())
                .fetch();
    }
}
