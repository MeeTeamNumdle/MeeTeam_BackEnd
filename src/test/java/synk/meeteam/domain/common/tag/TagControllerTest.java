package synk.meeteam.domain.common.tag;

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
import synk.meeteam.domain.common.tag.api.TagController;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.common.tag.service.TagService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TagControllerTest {
    @InjectMocks
    private TagController tagController;

    @Mock
    private TagService tagService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    void 태그자동완성검색_태그목록반환() throws Exception {
        //given
        final String url = "/tag/search";
        doReturn(TagFixture.createTagDtos()).when(tagService).searchByKeywordAndType("웹개발", 5, TagType.MEETEAM);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("keyword", "웹개발"));

        //then
        String expectName = "$[?(@.name=='%s')]";
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath(expectName, "웹개발").exists());
    }

    @Test
    void 강의명자동완성검색_강의명태그목록반환() throws Exception {
        //given
        final String url = "/tag/search/course";
        doReturn(TagFixture.createCourseTagDtos()).when(tagService).searchByKeywordAndType("응용", 5, TagType.COURSE);

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
    void 교수명자동완성검색_교수명태그목록반환() throws Exception {
        //given
        final String url = "/tag/search/professor";
        doReturn(TagFixture.createProfessorTagDtos()).when(tagService)
                .searchByKeywordAndType("문", 5, TagType.PROFESSOR);

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("keyword", "문"));

        //then
        String expectName = "$[?(@.name=='%s')]";
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath(expectName, "문승현").exists());
    }


}
