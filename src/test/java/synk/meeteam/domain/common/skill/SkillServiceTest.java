package synk.meeteam.domain.common.skill;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.exception.SkillException;
import synk.meeteam.domain.common.skill.repository.SkillRepository;
import synk.meeteam.domain.common.skill.service.SkillService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
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

    @Test
    public void 스킬목록조회_스킬목록반환() {
        //given
        String keyword = "자바";
        long limit = 3;

        doReturn(
                SkillFixture.createDtoByKeyword자바()
        ).when(skillRepository).findAllByKeywordAndTopLimit(keyword, limit);

        //when
        List<SkillDto> skillDtos = skillService.searchByKeyword(keyword, limit);

        //then
        assertThat(skillDtos).extracting("name").containsExactly("자바", "자바스크립트");
    }

}
