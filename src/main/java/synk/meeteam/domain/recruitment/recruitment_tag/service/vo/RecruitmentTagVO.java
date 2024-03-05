package synk.meeteam.domain.recruitment.recruitment_tag.service.vo;

import java.util.List;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;

public record RecruitmentTagVO(
        List<RecruitmentTag> recruitmentTags,
        String courseName,
        String courseProfessor
) {
    public static RecruitmentTagVO from(List<RecruitmentTag> recruitmentTag, String courseName,
                                        String courseProfessor) {
        return new RecruitmentTagVO(recruitmentTag, courseName, courseProfessor);
    }
}
