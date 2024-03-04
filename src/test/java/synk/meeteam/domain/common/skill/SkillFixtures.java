package synk.meeteam.domain.common.skill;

import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;

public class SkillFixtures {

    public static Skill createByName(final String name) {
        return new Skill(name);
    }

    public static List<Skill> createSkills() {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("스프링"));
        skills.add(new Skill("리엑트"));
        skills.add(new Skill("자바"));
        skills.add(new Skill("파이썬"));
        skills.add(new Skill("자바스크립트"));
        skills.add(new Skill("응용소프트웨어"));
        return skills;
    }

    public static List<SkillDto> createDtoByKeyword자바() {
        List<SkillDto> skillDtos = new ArrayList<>();
        skillDtos.add(new SkillDto(1L, "자바"));
        skillDtos.add(new SkillDto(2L, "자바스크립트"));
        return skillDtos;
    }
}
