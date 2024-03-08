package synk.meeteam.domain.common.tag.repository;

import java.util.List;
import synk.meeteam.domain.common.tag.dto.TagDto;

public interface TagRepositoryCustom {
    List<TagDto> findAllByRecruitmentId(Long postId);
}
