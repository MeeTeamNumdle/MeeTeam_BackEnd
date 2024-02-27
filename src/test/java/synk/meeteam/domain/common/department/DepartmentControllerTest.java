package synk.meeteam.domain.common.department;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
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
import synk.meeteam.domain.common.department.api.DepartmentController;
import synk.meeteam.domain.common.department.service.DepartmentService;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.exception.UniversityException;
import synk.meeteam.domain.common.university.exception.UniversityExceptionType;
import synk.meeteam.domain.common.university.service.UniversityService;
import synk.meeteam.global.common.exception.GlobalExceptionHandler;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {
    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private UniversityService universityService;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void 특정대학학과리스트조회_학과리스트반환_특정대학이주어질때() throws Exception {
        // given
        final String url = "/department";
        University university = new University(1L, "광운대학교", "kw.ac.kr");
        doReturn(DepartmentFixture.createDepartments()).when(departmentService).getDepartmentsByUniversity(university);
        doReturn(university).when(universityService).getUniversity(1L);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("university", "1"));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void 특정대학학과리스트조회_예외발생_유효하지않는대학이주어질때() throws Exception {
        final String url = "/department";

        doThrow(new UniversityException(UniversityExceptionType.BAD_REQUEST_UNIVERSITY_ID)).when(universityService)
                .getUniversity(1000L);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("university", "1000"));
        result.andExpect(status().isBadRequest());
    }
}
