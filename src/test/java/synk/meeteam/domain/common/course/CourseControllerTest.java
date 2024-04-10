package synk.meeteam.domain.common.course;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import synk.meeteam.domain.common.course.api.CourseController;
import synk.meeteam.domain.common.course.service.CourseService;
import synk.meeteam.domain.common.course.service.ProfessorService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseService;

    @Mock
    private ProfessorService professorService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void 강의명자동완성검색_강의명목록반환() throws Exception {
        //given
        final String url = "/course/search";
        doReturn(CourseFixture.createCourseDtos()).when(courseService).searchByKeyword("응용", 5);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("keyword", "응용"));

        //then
        String expectName = "$[?(@.name=='%s')]";
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath(expectName, "응용소프트웨어실습").exists());
    }

    @Test
    void 교수명자동완성검색_교수명목록반환() throws Exception {
        //given
        final String url = "/professor/search";
        doReturn(CourseFixture.createProfessorDtos()).when(professorService)
                .searchByKeyword("김", 5);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("keyword", "김"));

        //then
        String expectName = "$[?(@.name=='%s')]";
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath(expectName, "김용혁").exists());
    }
}
