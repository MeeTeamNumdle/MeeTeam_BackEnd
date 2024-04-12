package synk.meeteam.domain.common.skill;

import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;

public class SkillFixture {

    public static Skill crateSkill(String skillName) {
        return Skill.builder().name(skillName).build();
    }

    public static List<SkillDto> createDtoByKeyword자바() {
        List<SkillDto> skillDtos = new ArrayList<>();
        skillDtos.add(new SkillDto(1L, "자바"));
        skillDtos.add(new SkillDto(2L, "자바스크립트"));
        return skillDtos;
    }
}
