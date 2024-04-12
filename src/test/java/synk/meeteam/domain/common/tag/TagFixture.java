package synk.meeteam.domain.common.tag;

import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.common.tag.dto.SearchTagDto;
import synk.meeteam.domain.common.tag.dto.TagDto;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.entity.TagType;

public class TagFixture {
    public static String NAME_EXCEED_15 = "15자넘는태그이름을위해서 이렇게 긴 문자열을 만들게 되었습니다만";

    public static Tag createTag(String name, TagType type) {
        return Tag.builder()
                .name(name)
                .type(type)
                .build();
    }

    public static Tag createCoureNameTag(String name) {
        return Tag.builder()
                .name(name)
                .type(TagType.COURSE)
                .build();
    }

    public static Tag createProfessorTag(String name) {
        return Tag.builder()
                .name(name)
                .type(TagType.PROFESSOR)
                .build();
    }

    public static List<SearchTagDto> createTagDtos() {
        List<SearchTagDto> tagDtos = new ArrayList<>();
        tagDtos.add(new SearchTagDto(1L, "웹개발"));
        return tagDtos;
    }


    public static List<TagDto> createRecruitmentTags(Tag tag) {

        List<TagDto> recruitmentTags = new ArrayList<>();

        recruitmentTags.add(new TagDto(tag.getName(), tag.getType()));
        recruitmentTags.add(new TagDto("김용혁", TagType.PROFESSOR));
        recruitmentTags.add(new TagDto("응소실", TagType.COURSE));

        return recruitmentTags;
    }
}
