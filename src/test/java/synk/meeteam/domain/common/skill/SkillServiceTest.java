package synk.meeteam.domain.common.skill;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.exception.SkillException;
import synk.meeteam.domain.common.skill.repository.SkillRepository;
import synk.meeteam.domain.common.skill.service.SkillService;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {
    @InjectMocks
    private SkillService skillService;

    @Mock
    private SkillRepository skillRepository;

    @Test
    void 특정Skill조회_특정Skill반환_유효한Id입력값() {
        // given
        Long input = 2L;
        Skill skill = SkillFixture.crateSkill("spring");
        doReturn(skill).when(skillRepository).findByIdOrElseThrowException(input);

        // when
        Skill foundSkill = skillService.findBySkillId(input);

        // then
        Assertions.assertThat(foundSkill.getName()).isEqualTo(skill.getName());
    }

    @Test
    void 특정역할조회_예외발생_유효하지않은Id입력값() {
        // given
        Long input = 2L;
        doThrow(SkillException.class).when(skillRepository).findByIdOrElseThrowException(input);

        // when, then
        Assertions.assertThatThrownBy(() -> skillService.findBySkillId(input)).isInstanceOf(SkillException.class);
    }
}
