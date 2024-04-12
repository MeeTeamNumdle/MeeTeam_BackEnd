package synk.meeteam.domain.common.skill.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;

@Data
@Schema(name = "SkillDto", description = "스킬 Dto")
public class SkillDto {
    @Schema(description = "Skill Id", example = "1")
    private Long id;
    @Schema(description = "스킬 명", example = "파이썬")
    private String name;

    @QueryProjection
    public SkillDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SkillDto from(RecruitmentRoleSkill recruitmentRoleSkill) {
        return new SkillDto(recruitmentRoleSkill.getSkill().getId(), recruitmentRoleSkill.getSkill().getName());
    }
}
