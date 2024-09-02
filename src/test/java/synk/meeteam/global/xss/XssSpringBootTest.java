package synk.meeteam.global.xss;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CourseTagDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.RecruitmentRoleDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentPostResponseDto;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class XssSpringBootTest {
    private static final String RECRUITMENT_URL = "/recruitment/postings";

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${jwt.access.header}")
    private String accessHeader;

    private String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInVzZXJJZCI6IjRPYVZFNDIxRFN3UjYzeGZLZjZ2eEEiLCJpYXQiOjE3MTQ5ODM5MDQsImV4cCI6MjAyMjk4MzkwNH0.PsQHWlh-tV-FY3dk0zVwiiBCfyLn4LPbFylGcau1Eis";
    private String TOKEN_OTHER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInVzZXJJZCI6ImJfVmtjT05oTUNKSWJHbEQ2eW9Ua3ciLCJpYXQiOjE3MTQ5ODM5MDQsImV4cCI6MjAyMjk4MzkwNH0.pGrBWCYOrR2RKQfqKgG705I7NHqIlykUcYrKqhj_nOM";

    HttpHeaders headers;



    @BeforeEach
    void init() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(accessHeader, TOKEN);
    }

    @Test
    public void escape문자로치환_구인글생성_성공_xss공격() {
        HttpEntity request = new HttpEntity(headers);

        String title = "<li>content</li>";
        String expected = "&lt;li&gt;content&lt;/li&gt;";
        CreateRecruitmentPostRequestDto requestDto = createRequestDto_title(title);
        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<CreateRecruitmentPostResponseDto> responseEntity = restTemplate.postForEntity(
                RECRUITMENT_URL,
                requestEntity, CreateRecruitmentPostResponseDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        CreateRecruitmentPostResponseDto body = responseEntity.getBody();
        assertNotNull(body.recruitmentPostId());;

        ResponseEntity<GetRecruitmentPostResponseDto> verifyResponseEntity = restTemplate.exchange(
                RECRUITMENT_URL + "/{id}", HttpMethod.GET, request,
                GetRecruitmentPostResponseDto.class, body.recruitmentPostId());

        GetRecruitmentPostResponseDto verifyBody = verifyResponseEntity.getBody();
        assertEquals(verifyBody.title(), expected);
    }

    @Test
    public void escape문자로치환및LocalDate치환_구인글생성_성공_xss공격() {
        HttpEntity request = new HttpEntity(headers);

        String title = "<li>content</li>";
        String expected = "&lt;li&gt;content&lt;/li&gt;";
        CreateRecruitmentPostRequestDto requestDto = createRequestDto_title(title);
        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<CreateRecruitmentPostResponseDto> responseEntity = restTemplate.postForEntity(
                RECRUITMENT_URL,
                requestEntity, CreateRecruitmentPostResponseDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        CreateRecruitmentPostResponseDto body = responseEntity.getBody();
        assertNotNull(body.recruitmentPostId());;

        ResponseEntity<GetRecruitmentPostResponseDto> verifyResponseEntity = restTemplate.exchange(
                RECRUITMENT_URL + "/{id}", HttpMethod.GET, request,
                GetRecruitmentPostResponseDto.class, body.recruitmentPostId());

        GetRecruitmentPostResponseDto verifyBody = verifyResponseEntity.getBody();
        assertEquals(verifyBody.title(), expected);
        assertEquals(verifyBody.deadline(), LocalDate.of(2024, 2, 22).toString());
    }

    private CreateRecruitmentPostRequestDto createRequestDto_title(String title) {
        CourseTagDto courseTagDto = new CourseTagDto(true, "응소실", "김용혁");
        List<RecruitmentRoleDto> recruitmentRoleDtos = new ArrayList<>();
        recruitmentRoleDtos.add(new RecruitmentRoleDto(1L, 1, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(2L, 3, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(3L, 1, List.of(4L, 5L, 6L)));

        return new CreateRecruitmentPostRequestDto("교내", "프로젝트", LocalDate.of(2024, 2, 22), LocalDate.of(2024, 3, 15),
                LocalDate.of(2024, 5, 15), 1L, "온라인",
                courseTagDto, List.of("웹개발", "AI", "대학생", "구인"), recruitmentRoleDtos, title, "사람구합니당!!");
    }
}
