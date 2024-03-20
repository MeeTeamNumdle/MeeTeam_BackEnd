package synk.meeteam.domain.common.tag.repository;

import static synk.meeteam.domain.common.tag.entity.QTag.tag;
import static synk.meeteam.domain.recruitment.recruitment_tag.entity.QRecruitmentTag.recruitmentTag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.common.tag.dto.QSearchTagDto;
import synk.meeteam.domain.common.tag.dto.QTagDto;
import synk.meeteam.domain.common.tag.dto.SearchTagDto;
import synk.meeteam.domain.common.tag.dto.TagDto;
import synk.meeteam.domain.common.tag.entity.TagType;


@Repository
@RequiredArgsConstructor
public class TagRepositoryCustomImpl implements TagRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<TagDto> findAllByRecruitmentId(Long postId) {
        return queryFactory.select(new QTagDto(tag.id, tag.name, tag.type))
                .from(tag)
                .leftJoin(recruitmentTag)
                .on(tag.id.eq(recruitmentTag.tag.id))
                .where(recruitmentTag.recruitmentPost.id.eq(postId))
                .fetch();
    }

    @Override
    public List<SearchTagDto> findAllByKeywordAndTopLimitAndType(String keyword, long limit, TagType type) {
        return queryFactory
                .select(new QSearchTagDto(tag.id, tag.name))
                .from(tag)
                .where(tag.name.startsWith(keyword).and(tag.type.eq(type)))
                .limit(limit)
                .orderBy(tag.name.length().asc().nullsLast())
                .fetch();
    }
}
