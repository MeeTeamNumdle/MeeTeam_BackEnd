package synk.meeteam.domain.recruitment.recruitment_tag.service.vo;

import java.util.List;
import synk.meeteam.domain.common.tag.dto.TagDto;

public record RecruitmentTagVO(
        List<TagDto> recruitmentTags,
        String courseName,
        String courseProfessor
) {
    public static RecruitmentTagVO from(List<TagDto> recruitmentTags, String courseName,
                                        String courseProfessor) {
        return new RecruitmentTagVO(recruitmentTags, courseName, courseProfessor);
    }
}
