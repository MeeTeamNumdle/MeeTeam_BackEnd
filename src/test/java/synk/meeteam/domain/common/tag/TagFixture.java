package synk.meeteam.domain.common.tag;

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

    public static Tag createCoureNanmeTag(String name) {
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
}
