package synk.meeteam.domain.common.skill;

import synk.meeteam.domain.common.skill.entity.Skill;

public class SkillFixture {

    public static Skill crateSkill(String skillName) {
        return Skill.builder().name(skillName).build();
    }
}
