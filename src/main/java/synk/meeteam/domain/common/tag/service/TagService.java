package synk.meeteam.domain.common.tag.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.common.tag.dto.SearchTagDto;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.common.tag.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<SearchTagDto> searchByKeywordAndType(String keyword, long limit, TagType type) {
        return tagRepository.findAllByKeywordAndTopLimitAndType(keyword, limit, type);
    }
}
