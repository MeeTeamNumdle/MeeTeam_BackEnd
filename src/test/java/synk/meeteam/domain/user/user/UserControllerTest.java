package synk.meeteam.domain.user.user;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static synk.meeteam.domain.user.user.UserFixture.NICKNAME;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import synk.meeteam.domain.portfolio.portfolio.PortfolioFixture;
import synk.meeteam.domain.portfolio.portfolio.service.PortfolioService;
import synk.meeteam.domain.user.user.api.UserController;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.ProfileFacade;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.global.util.Encryption;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    ProfileFacade profileFacade;

    @Mock
    PortfolioService portfolioService;
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gson = gsonBuilder.setPrettyPrinting().create();
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void 실패_닉네임중복() throws Exception {
        // given
        final String url = "/user/search/check-duplicate";
        doReturn(false).when(userService).checkAvailableNickname(NICKNAME);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url).param("nickname", NICKNAME)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.isEnable").value(false));
    }

    @Test
    @WithMockUser
    void 유저프로필수정_유저프로필수정성공() throws Exception {
        //given
        final String url = "/user/profile";

        doNothing().when(profileFacade).editProfile(any(User.class), eq(UserFixture.createEditProfileDto()));
        try (MockedStatic<Encryption> utilities = Mockito.mockStatic(Encryption.class)) {
            utilities.when(() -> Encryption.encryptLong(any())).thenReturn("1234");
            //when
            final ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders.put(url)
                            .header("Authorization", "aaaaa")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(UserFixture.createEditProfileDto()))
            );
            //then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$").value("1234"));
        }
    }

    @Test
    void 유저프로필조회_조회성공() throws Exception {
        //given
        String encryptedId = "암호화된아이디";
        final String url = "/user/profile/" + encryptedId;
        doReturn(UserFixture.createReadProfile()).when(profileFacade).readProfile(encryptedId);
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url));
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.userName").value("민지"))
                .andExpect(jsonPath("$.nickname").value("mingi123"))
                .andExpect(jsonPath("$.isUserNamePublic").value(true))
                .andExpect(jsonPath("$.universityEmail.content").value("minji@kw.ac.kr"))
                .andExpect(jsonPath("$.awards[0].title").value("공공데이터 공모전"))
                .andExpect(jsonPath("$.links[1].description").value("Github"));
    }

    @Test
    void 내포트폴리오조회_조회성공() throws Exception {
        //given
        final String url = "/user/portfolios";
        doReturn(PortfolioFixture.createUserAllPortfolios()).when(portfolioService)
                .getSliceMyAllPortfolio(eq(1), eq(12), any());
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .param("page", "1")
        );
        //then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.pageInfo.page").value(1),
                jsonPath("$.pageInfo.size").value(12),
                jsonPath("$.pageInfo.hasNextPage").value(false),
                jsonPath("$.portfolios").isNotEmpty(),
                jsonPath("$.portfolios.*.title", hasItems("타이틀1", "타이틀2"))
        );
    }

}
