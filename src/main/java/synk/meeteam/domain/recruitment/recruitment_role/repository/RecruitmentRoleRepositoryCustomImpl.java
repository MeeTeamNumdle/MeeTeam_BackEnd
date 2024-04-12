package synk.meeteam.domain.recruitment.recruitment_role.repository;

import static synk.meeteam.domain.common.role.entity.QRole.role;
import static synk.meeteam.domain.recruitment.recruitment_role.entity.QRecruitmentRole.recruitmentRole;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.recruitment.recruitment_role.dto.AvailableRecruitmentRoleDto;
import synk.meeteam.domain.recruitment.recruitment_role.dto.QAvailableRecruitmentRoleDto;

@Repository
@RequiredArgsConstructor
public class RecruitmentRoleRepositoryCustomImpl implements RecruitmentRoleRepositoryCustom {
    private static final int ZERO = 0;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AvailableRecruitmentRoleDto> findAvailableRecruitmentRoleByRecruitmentId(Long postId) {
        Predicate predicate = recruitmentRole.count.subtract(recruitmentRole.recruitedCount).gt(ZERO);

        return queryFactory
                .select(new QAvailableRecruitmentRoleDto(recruitmentRole.id, role.name))
                .from(recruitmentRole)
                .leftJoin(role)
                .on(recruitmentRole.role.id.eq(role.id))
                .where(recruitmentRole.recruitmentPost.id.eq(postId), predicate)
                .fetch();
    }
}
