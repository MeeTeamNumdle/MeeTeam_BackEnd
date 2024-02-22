package synk.meeteam.domain.user.user;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static synk.meeteam.domain.user.user.UserFixtures.NICKNAME;

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
import synk.meeteam.domain.user.user.api.UserController;
import synk.meeteam.domain.user.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void 실패_닉네임중복() throws Exception {
        // given
        final String url = "/user/search/check-duplicate";
        doReturn(false).when(userService).checkDuplicateNickname(NICKNAME);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url).param("nickname", NICKNAME)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.isEnable").value(false));
    }

}
