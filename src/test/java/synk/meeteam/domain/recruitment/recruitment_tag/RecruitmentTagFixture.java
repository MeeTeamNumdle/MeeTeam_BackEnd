package synk.meeteam.domain.recruitment.recruitment_tag;

import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;

public class RecruitmentTagFixture {

    public static RecruitmentTag createRecruitmentTag(RecruitmentPost recruitmentPost, Tag tag) {
        return RecruitmentTag.builder()
                .recruitmentPost(recruitmentPost)
                .tag(tag)
                .build();
    }

    public static List<RecruitmentTag> createRecruitmentTags(RecruitmentPost recruitmentPost, Tag tag) {

        List<RecruitmentTag> recruitmentTags = new ArrayList<>();

        recruitmentTags.add(RecruitmentTag.builder()
                .recruitmentPost(recruitmentPost)
                .tag(tag)
                .build());
        recruitmentTags.add(RecruitmentTag.builder()
                .recruitmentPost(recruitmentPost)
                .tag(new Tag("김용혁", TagType.PROFESSOR))
                .build());

        recruitmentTags.add(RecruitmentTag.builder()
                .recruitmentPost(recruitmentPost)
                .tag(new Tag("응소실", TagType.COURSE))
                .build());

        return recruitmentTags;
    }
}
