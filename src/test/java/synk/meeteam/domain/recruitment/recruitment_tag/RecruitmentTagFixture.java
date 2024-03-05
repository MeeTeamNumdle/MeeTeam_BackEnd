package synk.meeteam.domain.recruitment.recruitment_tag;

import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;

public class RecruitmentTagFixture {

    public static RecruitmentTag createRecruitmentTag(RecruitmentPost recruitmentPost, Tag tag) {
        return RecruitmentTag.builder()
                .recruitmentPost(recruitmentPost)
                .tag(tag)
                .build();
    }
}
