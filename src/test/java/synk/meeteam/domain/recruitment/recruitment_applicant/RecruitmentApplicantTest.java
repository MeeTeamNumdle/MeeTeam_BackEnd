package synk.meeteam.domain.recruitment.recruitment_applicant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.request.SetLinkRequestDto;
import synk.meeteam.global.common.exception.ExceptionResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RecruitmentApplicantTest {

    private static final String RECRUITMENT_URL = "/recruitment/applicant";
    HttpHeaders headers;
    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;
    private String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInBsYXRmb3JtSWQiOiJEaTdsQ2hNR3hqWlZUYWk2ZDc2SG8xWUxEVV94TDh0bDFDZmRQTVY1U1FNIiwicGxhdGZvcm1UeXBlIjoiTkFWRVIiLCJpYXQiOjE3MDg1OTkyMTMsImV4cCI6MTgxNjU5OTIxM30.C9Rt8t2dM_9pmUIwyMiRwi2kZSXAFVJnjAPj2rTbQtw";
    private String TOKEN_OTHER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInBsYXRmb3JtSWQiOiJEaTdsQ2hNR3hqWlZUYWk2ZDc2SG8xWUxEVV94TDh0bDFDZmRQTVY1U1ExIiwicGxhdGZvcm1UeXBlIjoiTkFWRVIiLCJpYXQiOjE3MDg1OTkyMTMsImV4cCI6MTgxNjU5OTIxM30.ujVS6-qGhaOYJv0aPet3tgcc5iN93-k0Kv9w1rETFpA";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private DatabaseCleanUp databaseCleanUp;
    @Autowired
    private DataSource dataSource;

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
    void 링크설정_성공() {
        // given
        String kakaoLink = "카카오링크입니다.";
        Long postId = 1L;
        SetLinkRequestDto requestDto = new SetLinkRequestDto(kakaoLink);
        HttpEntity<SetLinkRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        // when

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                URI.create(RECRUITMENT_URL + "/" + postId.toString() + "/link"), HttpMethod.PUT,
                requestEntity, Void.class);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void 링크설정_예외발생_작성자가아닌경우() {
        // given
        String kakaoLink = "카카오링크입니다.";
        Long postId = 1L;
        headers.set(accessHeader, TOKEN_OTHER);
        SetLinkRequestDto requestDto = new SetLinkRequestDto(kakaoLink);
        HttpEntity<SetLinkRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        // when
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.exchange(
                URI.create(RECRUITMENT_URL + "/" + postId.toString() + "/link"), HttpMethod.PUT,
                requestEntity, ExceptionResponse.class);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    void 링크설정_예외발생_존재하지않은구인글경우() {
        // given
        String kakaoLink = "카카오링크입니다.";
        Long postId = 0L;
        SetLinkRequestDto requestDto = new SetLinkRequestDto(kakaoLink);
        HttpEntity<SetLinkRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        // when
        ResponseEntity<ExceptionResponse> responseEntity = restTemplate.exchange(
                URI.create(RECRUITMENT_URL + "/" + postId.toString() + "/link"), HttpMethod.PUT,
                requestEntity, ExceptionResponse.class);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
