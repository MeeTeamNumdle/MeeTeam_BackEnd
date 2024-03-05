package synk.meeteam.domain.recruitment.recruitment_post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE_EXCEED_40;
import static synk.meeteam.global.common.exception.GlobalExceptionType.INVALID_INPUT_VALUE;
import static synk.meeteam.global.entity.exception.EnumExceptionType.INVALID_CATEGORY_NAME;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CourseTagDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.RecruitmentRoleDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.global.common.exception.ExceptionResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RecruitmentPostTest {

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInBsYXRmb3JtSWQiOiJEaTdsQ2hNR3hqWlZUYWk2ZDc2SG8xWUxEVV94TDh0bDFDZmRQTVY1U1FNIiwicGxhdGZvcm1UeXBlIjoiTkFWRVIiLCJpYXQiOjE3MDg1OTkyMTMsImV4cCI6MTgxNjU5OTIxM30.C9Rt8t2dM_9pmUIwyMiRwi2kZSXAFVJnjAPj2rTbQtw";

    @Autowired
    private TestRestTemplate restTemplate;

    // @Notnull 체크 -> MethodArgumentNotValidException.class
    // uniqueContrants 체크 -> DataIntegrityViolationException.class
    // LocalDate 체크, Long 체크, List<Long> 체크 -> HttpMessageNotReadableException.class
    // List<String> 체크 -> 좀 애매하다..

    @Test
    void 구인글생성_생성된구인글Id반환_정상입력경우() {
        CreateRecruitmentPostRequestDto requestDto = createRequestDto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(accessHeader, TOKEN);

        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<CreateRecruitmentPostResponseDto> responseEntity = restTemplate.postForEntity(
                "/recruitment/post",
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(accessHeader, TOKEN);

        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity(
                "/recruitment/post",
                requestEntity, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALID_INPUT_VALUE.name(), responseEntity.getBody().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"유형", " 프로젝트", "", " ", "프로젝트 ", "스터디;"})
    void 구인글생성_예외발생_올바르지않은유형일경우(String category) {
        CreateRecruitmentPostRequestDto requestDto = createRequestDto_category(category);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(accessHeader, TOKEN);

        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity(
                "/recruitment/post",
                requestEntity, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALID_CATEGORY_NAME.name(), responseEntity.getBody().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"유형", " 프로젝트", "", " ", "프로젝트 ", "스터디;"})
    void 구인글생성_예외발생_올바르지않은날짜일경우(String date) {
        CreateRecruitmentPostRequestDto requestDto = createRequestDto_category(date);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(accessHeader, TOKEN);

        HttpEntity<CreateRecruitmentPostRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.postForEntity(
                "/recruitment/post",
                requestEntity, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALID_CATEGORY_NAME.name(), responseEntity.getBody().getName());
    }


    private CreateRecruitmentPostRequestDto createRequestDto() {
        List<LocalDate> localDates = new ArrayList<>();
        localDates.add(LocalDate.of(2024, 3, 15));
        localDates.add(LocalDate.of(2024, 5, 15));

        CourseTagDto courseTagDto = new CourseTagDto(true, "응소실", "김용혁");
        List<RecruitmentRoleDto> recruitmentRoleDtos = new ArrayList<>();
        recruitmentRoleDtos.add(new RecruitmentRoleDto(1L, 1, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(2L, 3, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(3L, 1, List.of(4L, 5L, 6L)));

        return new CreateRecruitmentPostRequestDto("교내", "프로젝트", LocalDate.of(2024, 2, 22), localDates, 1L, "온라인",
                courseTagDto, List.of("웹개발", "AI", "대학생", "구인"), recruitmentRoleDtos, "정상적인 제목", "사람구합니당!!");
    }

    private CreateRecruitmentPostRequestDto createRequestDto_title(String title) {
        List<LocalDate> localDates = new ArrayList<>();
        localDates.add(LocalDate.of(2024, 3, 15));
        localDates.add(LocalDate.of(2024, 5, 15));

        CourseTagDto courseTagDto = new CourseTagDto(true, "응소실", "김용혁");
        List<RecruitmentRoleDto> recruitmentRoleDtos = new ArrayList<>();
        recruitmentRoleDtos.add(new RecruitmentRoleDto(1L, 1, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(2L, 3, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(3L, 1, List.of(4L, 5L, 6L)));

        return new CreateRecruitmentPostRequestDto("교내", "프로젝트", LocalDate.of(2024, 2, 22), localDates, 1L, "온라인",
                courseTagDto, List.of("웹개발", "AI", "대학생", "구인"), recruitmentRoleDtos, title, "사람구합니당!!");
    }

    private CreateRecruitmentPostRequestDto createRequestDto_category(String category) {
        List<LocalDate> localDates = new ArrayList<>();
        localDates.add(LocalDate.of(2024, 3, 15));
        localDates.add(LocalDate.of(2024, 5, 15));

        CourseTagDto courseTagDto = new CourseTagDto(true, "응소실", "김용혁");
        List<RecruitmentRoleDto> recruitmentRoleDtos = new ArrayList<>();
        recruitmentRoleDtos.add(new RecruitmentRoleDto(1L, 1, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(2L, 3, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(3L, 1, List.of(4L, 5L, 6L)));

        return new CreateRecruitmentPostRequestDto("교내", category, LocalDate.of(2024, 2, 22),
                localDates, 1L, "온라인", courseTagDto, List.of("웹개발", "AI", "대학생", "구인"),
                recruitmentRoleDtos, "정상제목입니다.", "사람구합니당!!");
    }

    private CreateRecruitmentPostRequestDto createRequestDto_date(String date) {
        List<LocalDate> localDates = new ArrayList<>();
        localDates.add(LocalDate.of(2024, 3, 15));
        localDates.add(LocalDate.of(2024, 5, 15));

        CourseTagDto courseTagDto = new CourseTagDto(true, "응소실", "김용혁");
        List<RecruitmentRoleDto> recruitmentRoleDtos = new ArrayList<>();
        recruitmentRoleDtos.add(new RecruitmentRoleDto(1L, 1, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(2L, 3, List.of(1L, 2L, 3L)));
        recruitmentRoleDtos.add(new RecruitmentRoleDto(3L, 1, List.of(4L, 5L, 6L)));

        return new CreateRecruitmentPostRequestDto("교내", "프로젝트", LocalDate.of(2024, 2, 22),
                localDates, 1L, "온라인", courseTagDto, List.of("웹개발", "AI", "대학생", "구인"),
                recruitmentRoleDtos, "정상제목입니다.", "사람구합니당!!");
    }
}
