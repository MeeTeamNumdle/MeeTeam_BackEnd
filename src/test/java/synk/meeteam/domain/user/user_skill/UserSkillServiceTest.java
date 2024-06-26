package synk.meeteam.domain.user.user_skill;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.repository.SkillRepository;
import synk.meeteam.domain.user.user_skill.entity.UserSkill;
import synk.meeteam.domain.user.user_skill.repository.UserSkillRepository;
import synk.meeteam.domain.user.user_skill.service.UserSkillService;
import synk.meeteam.domain.user.user_skill.service.UserSkillServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserSkillServiceTest {
    @Mock
    UserSkillService userSkillService;

    @InjectMocks
    UserSkillServiceImpl userSkillServiceImpl;

    @Mock
    UserSkillRepository userSkillRepository;

    @Mock
    SkillRepository skillRepository;

    @BeforeEach
    void setup() {
        userSkillService = userSkillServiceImpl;
    }

    @Test
    void 유저스킬변경_유저스킬변경성공() {

        //given
        doNothing().when(userSkillRepository).deleteAllByCreatedBy(anyLong());
        doReturn(UserSkillFixture.createUserSkillFixture()).when(userSkillRepository).saveAll(anyList());
        doReturn(UserSkillFixture.createSkill()).when(skillRepository).findAllById(anyList());

        //when
        List<UserSkill> userSkills = userSkillService.changeUserSkillBySkill(1L, List.of(1L, 2L));

        //then
        assertThat(userSkills)
                .extracting("skill")
                .extracting("name")
                .containsExactly("스킬1", "스킬2");

    }

    @Test
    void 유저스킬조회_유저스킬조회성공() {
        //given
        doReturn(UserSkillFixture.createSkillDto()).when(userSkillRepository).findSkillDtoByCreatedBy(anyLong());
        //when
        List<SkillDto> userSkill = userSkillService.getUserSKill(anyLong());
        //then
        Assertions.assertThat(userSkill).extracting("name").containsExactly("스킬1", "스킬2");
    }
}
