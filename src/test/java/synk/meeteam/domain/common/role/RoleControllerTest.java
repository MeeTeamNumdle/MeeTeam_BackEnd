package synk.meeteam.domain.common.role;

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
import synk.meeteam.domain.common.role.api.RoleController;
import synk.meeteam.domain.common.role.service.RoleService;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    void 역할자동완성검색_역할목록반환() throws Exception {
        //given
        final String url = "/role/search";
        doReturn(RoleFixture.createRoleDtos()).when(roleService).searchByKeyword("웹 개발자", 5);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("keyword", "웹 개발자"));

        //then
        String expectName = "$[?(@.name=='%s')]";
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath(expectName, "웹 개발자").exists());
    }
}
