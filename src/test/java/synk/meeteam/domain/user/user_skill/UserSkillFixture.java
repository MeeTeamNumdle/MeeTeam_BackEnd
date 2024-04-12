package synk.meeteam.domain.user.user_skill;

import java.util.List;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.user.user_skill.entity.UserSkill;

public class UserSkillFixture {

    public static List<UserSkill> createUserSkillFixture() {
        return List.of(
                new UserSkill(new Skill("스킬1")),
                new UserSkill(new Skill("스킬2"))
        );
    }

    public static List<Skill> createSkill() {
        return List.of(
                new Skill("스킬1"),
                new Skill("스킬2")
        );
    }

    public static List<SkillDto> createSkillDto() {
        return List.of(
                new SkillDto(1L, "스킬1"),
                new SkillDto(2L, "스킬2")
        );
    }
}
