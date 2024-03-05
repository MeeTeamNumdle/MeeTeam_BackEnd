package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;

@Schema(name = "TagDto", description = "구인태그 Dto")
public record TagDto(
        @Schema(description = "구인 태그 id", example = "1")
        Long id,
        @Schema(description = "구인 태그 이름", example = "대학생")
        String name
) {
        public static TagDto from(RecruitmentTag recruitmentTag) {
                return new TagDto(recruitmentTag.getTag().getId(), recruitmentTag.getTag().getName());
        }
}
