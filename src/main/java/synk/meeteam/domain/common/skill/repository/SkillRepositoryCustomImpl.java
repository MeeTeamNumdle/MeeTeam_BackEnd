package synk.meeteam.domain.common.skill.repository;

import static synk.meeteam.domain.common.skill.entity.QSkill.skill;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.common.skill.dto.QSkillDto;
import synk.meeteam.domain.common.skill.dto.SkillDto;

@Repository
@RequiredArgsConstructor
public class SkillRepositoryCustomImpl implements SkillRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<SkillDto> findAllByKeywordAndTopLimit(String keyword, long limit) {
        return queryFactory
                .select(new QSkillDto(skill.id, skill.name))
                .from(skill)
                .where(skill.name.contains(keyword))
                .limit(limit)
                .orderBy(skill.name.asc().nullsLast())
                .fetch();
    }

}
