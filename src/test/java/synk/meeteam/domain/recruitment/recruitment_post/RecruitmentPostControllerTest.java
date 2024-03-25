package synk.meeteam.domain.recruitment.recruitment_post;

import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import synk.meeteam.domain.recruitment.recruitment_post.api.RecruitmentPostController;
import synk.meeteam.domain.recruitment.recruitment_post.facade.RecruitmentPostFacade;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;

@ExtendWith(MockitoExtension.class)
public class RecruitmentPostControllerTest {

    @InjectMocks
    private RecruitmentPostController recruitmentPostController;

    @Mock
    private RecruitmentPostFacade recruitmentPostFacade;

    @Mock
    private RecruitmentPostService recruitmentPostService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(recruitmentPostController)
                .build();
    }

    @Test
    void 구인글생성_생성된구인글id반환_정상입력의경우() {
        // given

        // when

        // then
    }

    /**
     *  아래 메서드는 인증 유저 객체를 받아오는 이슈가 해결되지 않았다.
     * */

//    @Test
//    void 구인마감_예외발생_작성자가아닌경우() throws Exception{
//        // given
//        //SecurityContextHelper.setAuthentication();
//        University university = new University(1L, "광운대", "kw.ac.kr");
//        Department department = new Department(1L, university, "소프트웨어학부");
//        UserDetails userDetails = new CustomAuthUser(
//                new User("mikekks", "송민규", NICKNAME, "12", "qwe",
//                        2018, "Qwe", Authority.USER,
//                        PlatformType.NAVER, "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM", university, department),
//                Authority.USER);
//        final String url = "/recruitment/postings/{id}/close";
//        doReturn(RecruitmentPostFixture.createRecruitmentPost("정상제목")).when(recruitmentPostService).closeRecruitment(any(Long.class), any(Long.class));
//
//        // when
//        MockHttpServletRequestBuilder principal1 = MockMvcRequestBuilders.patch(url, "1")
//                .with(SecurityMockMvcRequestPostProcessors.user(userDetails));
//
//        final ResultActions resultActions = mockMvc.perform(principal1);
//
//        // then
//        resultActions.andExpect(status().isOk());
//    }
}
