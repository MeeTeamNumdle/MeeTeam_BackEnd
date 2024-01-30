package synk.meeteam.domain.tag.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.tag.dto.request.CreateCourseTagRequestDto;
import synk.meeteam.domain.tag.entity.Tag;
import synk.meeteam.domain.tag.entity.TagType;
import synk.meeteam.domain.tag.repository.TagRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public List<Tag> crateTags(List<String> tagNames, CreateCourseTagRequestDto courseTagRequestDto) {
        List<Tag> tags = new ArrayList<>();

        if (courseTagRequestDto.isCourse()) {
            Tag courseTag = createTag(courseTagRequestDto.courseTagName(), TagType.COURSE);
            tags.add(courseTag);
        }

        for (String tagName : tagNames) {
            Tag tag = createTag(tagName, TagType.MEETEAM);
            tags.add(tag);
        }

        return tags;
    }

    private Tag createTag(String tagName, TagType tagType){
        Tag tag = tagRepository.findByName(tagName).orElse(null);
        if(tag == null){
            Tag newTag = Tag.builder()
                    .name(tagName)
                    .type(tagType)
                    .build();
            return tagRepository.save(newTag);
        }
        return tag;
    }
}
