package synk.meeteam.domain.recruitment.recruitment_post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE_EXCEED_40;
import static synk.meeteam.domain.recruitment.recruitment_role.exception.RecruitmentRoleExceptionType.INVALID_RECRUITMENT_ROLE_ID;
import static synk.meeteam.global.common.exception.GlobalExceptionType.INVALID_INPUT_VALUE;
import static synk.meeteam.global.entity.exception.EnumExceptionType.INVALID_CATEGORY_NAME;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.DatabaseCleanUp;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.ApplyRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CourseTagDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.RecruitmentRoleDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetApplyInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentRoleResponseDto;
import synk.meeteam.global.common.exception.ExceptionResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RecruitmentPostTest {

    private static final String RECRUITMENT_URL = "/recruitment/postings";

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInBsYXRmb3JtSWQiOiJEaTdsQ2hNR3hqWlZUYWk2ZDc2SG8xWUxEVV94TDh0bDFDZmRQTVY1U1FNIiwicGxhdGZvcm1UeXBlIjoiTkFWRVIiLCJpYXQiOjE3MDg1OTkyMTMsImV4cCI6MTgxNjU5OTIxM30.C9Rt8t2dM_9pmUIwyMiRwi2kZSXAFVJnjAPj2rTbQtw";

    @Autowired
    private TestRestTemplate restTemplate;
    HttpHeaders headers;
    @Autowired
    private DatabaseCleanUp databaseCleanUp;
    @Autowired
    private DataSource dataSource;

    // @Notnull 체크 -> MethodArgumentNotValidException.class
    // uniqueContrants 체크 -> DataIntegrityViolationException.class
    // LocalDate 체크, Long 체크, List<Long> 체크 -> HttpMessageNotReadableException.class
    // List<String> 체크 -> 좀 애매하다..

    @BeforeEach
    void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(accessHeader, TOKEN);
        databaseCleanUp.clear();
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data.sql"));
        populator.execute(dataSource);
    }


    @Test
    void 구인글생성_생성된구인글Id반환_정상입력경우() {
        CreateRecruitmentPostRequestDto requestDto = createRequestDto();
        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<CreateRecruitmentPostResponseDto> responseEntity = restTemplate.postForEntity(
                RECRUITMENT_URL,
                requestEntity, CreateRecruitmentPostResponseDto.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        CreateRecruitmentPostResponseDto body = responseEntity.getBody();
        assertNotNull(body.recruitmentPostId());
    }

    @ParameterizedTest
    @ValueSource(strings = {TITLE_EXCEED_40, "  ㅈ"})
    void 구인글생성_예외발생_구인글제목이5자미만40자넘는경우(String title) {
        CreateRecruitmentPostRequestDto requestDto = createRequestDto_title(title);
        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity(
                RECRUITMENT_URL,
                requestEntity, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALID_INPUT_VALUE.name(), responseEntity.getBody().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"유형", " 프로젝트", "", " ", "프로젝트 ", "스터디;"})
    void 구인글생성_예외발생_올바르지않은유형일경우(String category) {
        CreateRecruitmentPostRequestDto requestDto = createRequestDto_category(category);
        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity(
                RECRUITMENT_URL,
                requestEntity, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALID_CATEGORY_NAME.name(), responseEntity.getBody().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"유형", " 프로젝트", "", " ", "프로젝트 ", "스터디;"})
    void 구인글생성_예외발생_올바르지않은날짜일경우(String date) {
        CreateRecruitmentPostRequestDto requestDto = createRequestDto_category(date);
        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity(
                RECRUITMENT_URL,
                requestEntity, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALID_CATEGORY_NAME.name(), responseEntity.getBody().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"qwd", " ", "2fd2"})
    void 신청가능역할조회_예외발생_유효하지않은게시글id일경우(String date) {
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.exchange(
                RECRUITMENT_URL + "/{id}/apply-info", HttpMethod.GET, request,
                ExceptionResponse.class, date);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALID_INPUT_VALUE.name(), responseEntity.getBody().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1000", "-1"})
    void 신청가능역할조회_빈리스트반환_신청가능한역할없는경우(String date) {
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<GetApplyInfoResponseDto> responseEntity = restTemplate.exchange(
                RECRUITMENT_URL + "/{id}/apply-info", HttpMethod.GET, request,
                GetApplyInfoResponseDto.class, date);

        Assertions.assertThat(responseEntity.getBody().recruitmentRoles().size()).isEqualTo(0);
    }

    @Test
    void 구인신청_성공_정상경우() {
        HttpEntity request = new HttpEntity(headers);
        Long applyRoleId = 1L;

        ResponseEntity<GetRecruitmentPostResponseDto> prevResponseEntity = restTemplate.exchange(
                RECRUITMENT_URL + "/{id}", HttpMethod.GET, request,
                GetRecruitmentPostResponseDto.class, 1);

        GetRecruitmentRoleResponseDto prevRole = prevResponseEntity.getBody().recruitmentRoles().stream()
                .filter(role -> role.roleName().equals("소프트웨어 엔지니어"))
                .findAny().orElse(null);

        ///// 실행 /////
        ApplyRecruitmentRequestDto requestDto = new ApplyRecruitmentRequestDto(applyRoleId, "저 하고 싶어용");
        HttpEntity<ApplyRecruitmentRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ApplyRecruitmentRequestDto> responseEntity = restTemplate.postForEntity(
                URI.create(RECRUITMENT_URL + "/1/apply"),
                requestEntity, ApplyRecruitmentRequestDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ///// 실행 /////

        ResponseEntity<GetRecruitmentPostResponseDto> curResponseEntity = restTemplate.exchange(
                RECRUITMENT_URL + "/{id}", HttpMethod.GET, request,
                GetRecruitmentPostResponseDto.class, 1);

        GetRecruitmentRoleResponseDto curRole = curResponseEntity.getBody().recruitmentRoles().stream()
                .filter(role -> role.roleName().equals("소프트웨어 엔지니어"))
                .findAny().orElse(null);

        Assertions.assertThat(curRole.applicantCount()).isEqualTo(prevRole.applicantCount() + 1);
    }

    @Test
    void 구인신청_예외발생_이미신청한구인글경우() {
        Long applyRoleId = 1L;
        ApplyRecruitmentRequestDto requestDto = new ApplyRecruitmentRequestDto(applyRoleId, "저 하고 싶어용");
        HttpEntity<ApplyRecruitmentRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ApplyRecruitmentRequestDto> responseEntity = restTemplate.postForEntity(
                URI.create(RECRUITMENT_URL + "/1/apply"),
                requestEntity, ApplyRecruitmentRequestDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResponseEntity<ExceptionResponse> twiceResponseEntity = restTemplate.postForEntity(
                URI.create(RECRUITMENT_URL + "/1/apply"),
                requestEntity, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, twiceResponseEntity.getStatusCode());
        assertEquals(INVALID_INPUT_VALUE.name(), twiceResponseEntity.getBody().getName());
    }

    @Test
    void 구인신청_예외발생_유효하지않은구인역할id경우() {
        Long applyRoleId = -1L;
        ApplyRecruitmentRequestDto requestDto = new ApplyRecruitmentRequestDto(applyRoleId, "저 하고 싶어용");
        HttpEntity<ApplyRecruitmentRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ExceptionResponse> twiceResponseEntity = restTemplate.postForEntity(
                URI.create(RECRUITMENT_URL + "/1/apply"),
                requestEntity, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, twiceResponseEntity.getStatusCode());
        assertEquals(INVALID_RECRUITMENT_ROLE_ID.name(), twiceResponseEntity.getBody().getName());
    }

    @Test
    void 구인글수정_성공() {
        Long postId = 1L;
        CreateRecruitmentPostRequestDto requestDto = createRequestDto_title("수정된 제목입니다.");
        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        restTemplate.put(RECRUITMENT_URL + "/1", requestEntity);

        HttpEntity<CreateRecruitmentPostRequestDto> checkRequestEntity = new HttpEntity<>(headers);

        ResponseEntity<GetRecruitmentPostResponseDto> responseEntity = restTemplate.exchange(RECRUITMENT_URL + "/1",
                HttpMethod.GET, checkRequestEntity, GetRecruitmentPostResponseDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        GetRecruitmentPostResponseDto body = responseEntity.getBody();
        assertEquals(body.title(), "수정된 제목입니다.");
    }

    @Test
    void 구인글북마크_예외발생_이미북마크한경우() {
        Long postId = 1L;
        HttpEntity<ApplyRecruitmentRequestDto> requestEntity = new HttpEntity<>(headers);

        restTemplate.postForEntity(
                URI.create(RECRUITMENT_URL + "/1/bookmark"),
                requestEntity, ExceptionResponse.class);

        ResponseEntity<ExceptionResponse> twiceResponseEntity = restTemplate.postForEntity(
                URI.create(RECRUITMENT_URL + "/1/bookmark"),
                requestEntity, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, twiceResponseEntity.getStatusCode());

    }

    @Test
    void 구인글북마크취소_예외발생_북마크한기록이없는경우() {
        HttpEntity<ApplyRecruitmentRequestDto> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(URI.create(RECRUITMENT_URL + "/1/bookmark"),
                HttpMethod.DELETE, requestEntity,
                Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    ///////// Dto 생성 로직 /////////

    private CreateRecruitmentPostRequestDto createRequestDto() {
        CourseTagDto courseTagDto = new CourseTagDto(true, "응소실", "김용혁");
        List<RecruitmentRoleDto> recruitmentRoleDtos = new ArrayList<>();
        recruitmentRoleDtos.add(new RecruitmentRoleDto(1L, 1, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(2L, 3, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(3L, 1, List.of(4L, 5L, 6L)));

        return new CreateRecruitmentPostRequestDto("교내", "프로젝트", LocalDate.of(2024, 2, 22),
                LocalDate.of(2024, 3, 15), LocalDate.of(2024, 5, 15), 1L, "온라인",
                courseTagDto, List.of("웹개발", "AI", "대학생", "구인"), recruitmentRoleDtos, "정상적인 제목", "사람구합니당!!");
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

    private CreateRecruitmentPostRequestDto createRequestDto_category(String category) {
        CourseTagDto courseTagDto = new CourseTagDto(true, "응소실", "김용혁");
        List<RecruitmentRoleDto> recruitmentRoleDtos = new ArrayList<>();
        recruitmentRoleDtos.add(new RecruitmentRoleDto(1L, 1, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(2L, 3, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(3L, 1, List.of(4L, 5L, 6L)));

        return new CreateRecruitmentPostRequestDto("교내", category, LocalDate.of(2024, 2, 22),
                LocalDate.of(2024, 3, 15), LocalDate.of(2024, 5, 15), 1L, "온라인", courseTagDto,
                List.of("웹개발", "AI", "대학생", "구인"),
                recruitmentRoleDtos, "정상제목입니다.", "사람구합니당!!");
    }

    private CreateRecruitmentPostRequestDto createRequestDto_date(String date) {
        CourseTagDto courseTagDto = new CourseTagDto(true, "응소실", "김용혁");
        List<RecruitmentRoleDto> recruitmentRoleDtos = new ArrayList<>();
        recruitmentRoleDtos.add(new RecruitmentRoleDto(1L, 1, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(2L, 3, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(3L, 1, List.of(4L, 5L, 6L)));

        return new CreateRecruitmentPostRequestDto("교내", "프로젝트", LocalDate.of(2024, 2, 22),
                LocalDate.of(2024, 3, 15), LocalDate.of(2024, 5, 15), 1L, "온라인", courseTagDto,
                List.of("웹개발", "AI", "대학생", "구인"),
                recruitmentRoleDtos, "정상제목입니다.", "사람구합니당!!");
    }
}
