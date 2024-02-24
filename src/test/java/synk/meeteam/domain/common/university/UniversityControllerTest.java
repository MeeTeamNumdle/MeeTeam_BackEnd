package synk.meeteam.domain.common.university;

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
import synk.meeteam.domain.common.university.api.UniversityController;
import synk.meeteam.domain.common.university.service.UniversityService;

@ExtendWith(MockitoExtension.class)
public class UniversityControllerTest {

    @InjectMocks
    private UniversityController universityController;

    @Mock
    private UniversityService universityService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(universityController)
                .build();
    }

    @Test
    // given 생략(when과 겹친다고 생각)
    public void 대학리스트조회_대학리스트Dto반환() throws Exception {
        // given
        final String url = "/university";
        doReturn(UniversityFixture.createUniversities()).when(universityService).getUniversities();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());

    }
}
