package synk.meeteam.domain.user.user_skill.repository;

import static synk.meeteam.domain.common.skill.entity.QSkill.skill;
import static synk.meeteam.domain.user.user_skill.entity.QUserSkill.userSkill;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.common.skill.dto.QSkillDto;
import synk.meeteam.domain.common.skill.dto.SkillDto;

@Repository
@RequiredArgsConstructor
public class UserSkillCustomRepositoryImpl implements UserSkillCustomRepository {
    private final JPAQueryFactory queryFactory;

    public List<SkillDto> findSkillDtoByCreatedBy(Long userId) {
        return queryFactory
                .select(new QSkillDto(skill.id, skill.name))
                .from(userSkill)
                .join(skill)
                .on(userSkill.skill.id.eq(skill.id))
                .fetchJoin()
                .where(userSkill.createdBy.eq(userId))
                .orderBy(userSkill.createdAt.asc())
                .fetch();
    }
}
