package synk.meeteam.domain.common.skill;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.repository.SKillRepository;
import synk.meeteam.domain.common.skill.service.SkillService;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

    @InjectMocks
    SkillService skillService;

    @Spy
    SKillRepository sKillRepository;

    @Test
    public void 스킬목록조회_스킬목록반환() {
        //given
        String keyword = "자바";
        long limit = 3;

        doReturn(
                SkillFixtures.createDtoByKeyword자바()
        ).when(sKillRepository).findAllByKeywordAndTopLimit(keyword, limit);

        //when
        List<SkillDto> skillDtos = skillService.searchByKeyword(keyword, limit);

        //then
        assertThat(skillDtos).extracting("name").containsExactly("자바", "자바스크립트");
    }

}
