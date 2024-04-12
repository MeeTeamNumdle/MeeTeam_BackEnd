package synk.meeteam.domain.user.user_skill;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.repository.SkillRepository;
import synk.meeteam.domain.user.user_skill.entity.UserSkill;
import synk.meeteam.domain.user.user_skill.repository.UserSkillRepository;

@DataJpaTest
@ActiveProfiles("test")
public class UserSkillRepositoryTest {

    @Autowired
    UserSkillRepository userSkillRepository;

    @Autowired
    SkillRepository skillRepository;

    @Test
    void 유저스킬목록조회_조회성공() {
        //given
        Long userId = 1L;
        Skill skill = skillRepository.findById(1L).get();
        UserSkill userSkill = new UserSkill(skill);
        userSkill.setCreatedBy(1L);
        userSkillRepository.save(userSkill);
        //when
        List<SkillDto> skills = userSkillRepository.findSkillDtoByCreatedBy(userId);

        //then
        assertThat(skills).extracting("name").containsExactly(skill.getName());
    }

}
