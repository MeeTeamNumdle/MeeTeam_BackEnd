package synk.meeteam.domain.common.skill;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import synk.meeteam.domain.common.skill.api.SkillController;
import synk.meeteam.domain.common.skill.service.SkillService;

@ExtendWith(MockitoExtension.class)
public class SkillControllerTest {
    @InjectMocks
    private SkillController skillController;

    @Mock
    private SkillService skillService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(skillController).build();
    }

    @Test
    public void 스킬자동완성검색_스킬결과반환() throws Exception {
        //given
        final String url = "/skill/list";
        doReturn(SkillFixtures.createDtoByKeyword자바()).when(skillService).searchByKeyword("자바", 5);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("keyword", "자바"));

        //then
        String expectName = "$[?(@.name=='%s')]";
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists())
                .andExpect(jsonPath(expectName, "자바").exists())
                .andExpect(jsonPath(expectName, "자바스크립트").exists());
    }


}
