package synk.meeteam.domain.common.skill.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SkillDto", description = "스킬 Dto")
public record SkillDto(
        @Schema(description = "Skill Id", example = "1")
        Long id,
        @Schema(description = "스킬 명", example = "파이썬")
        String name
) {
    public static SkillDto of(Long id, String name) {
        return new SkillDto(id, name);
    }
}
