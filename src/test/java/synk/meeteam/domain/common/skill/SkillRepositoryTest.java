package synk.meeteam.domain.common.skill;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.exception.SkillException;
import synk.meeteam.domain.common.skill.repository.SkillRepository;

@DataJpaTest
@ActiveProfiles("test")
public class SkillRepositoryTest {
    @Autowired
    private SkillRepository skillRepository;

    @Test
    void 특정Skill조회_특정Skill반환_유효한Id입력값() {
        // given
        Long input = 1L;

        // when
        Skill skill = skillRepository.findByIdOrElseThrowException(input);

        // then
        Assertions.assertThat(skill).isNotNull();
    }

    @Test
    void 특정역할조회_예외발생_유효하지않은Id입력값() {
        // given
        Long input = 14L;

        // when, then
        Assertions.assertThatThrownBy(() -> skillRepository.findByIdOrElseThrowException(input))
                .isInstanceOf(SkillException.class);
    }
}
