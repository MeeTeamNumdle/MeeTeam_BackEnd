package synk.meeteam.domain.user.user;

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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import synk.meeteam.domain.user.user.api.UserController;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.ProfileFacade;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.security.filter.JwtAuthenticationFilter;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    ProfileFacade profileFacade;

    @Spy
    JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockMvc mockMvc;
    private Gson gson;
    HttpHeaders headers;
    private String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInBsYXRmb3JtSWQiOiJEaTdsQ2hNR3hqWlZUYWk2ZDc2SG8xWUxEVV94TDh0bDFDZmRQTVY1U1FNIiwicGxhdGZvcm1UeXBlIjoiTkFWRVIiLCJpYXQiOjE3MDg1OTkyMTMsImV4cCI6MTgxNjU5OTIxM30.C9Rt8t2dM_9pmUIwyMiRwi2kZSXAFVJnjAPj2rTbQtw";

    @BeforeEach
    public void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gson = gsonBuilder.setPrettyPrinting().create();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Autherization", TOKEN);
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
    void 유저프로필수정_유저프로필수정성공() throws Exception {
        //given
        final String url = "/user/profile";

        doNothing().when(profileFacade).editProfile(any(User.class), eq(UserFixture.createEditProfileDto()));
        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .headers(headers)
                        .content(gson.toJson(UserFixture.createEditProfileDto()))
        );
        //then
        resultActions.andExpect(status().isOk());
    }

}
