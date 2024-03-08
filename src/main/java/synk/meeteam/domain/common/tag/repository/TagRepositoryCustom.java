package synk.meeteam.domain.common.tag.repository;

import java.util.List;
import synk.meeteam.domain.common.tag.dto.SearchTagDto;
import synk.meeteam.domain.common.tag.dto.TagDto;
import synk.meeteam.domain.common.tag.entity.TagType;

public interface TagRepositoryCustom {
    List<TagDto> findAllByRecruitmentId(Long postId);

    List<SearchTagDto> findAllByKeywordAndTopLimitAndType(String keyword, long limit, TagType type);

}