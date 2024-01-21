package synk.meeteam.security.jwt.utils;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

public abstract class RequestUtils {

    @Value("${jwt.access.header}")
    private static String accessHeader = "Authorization";
    public static final String BEARER_HEADER = "Bearer ";


    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static boolean isContainsAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader(accessHeader);
        return authorization != null
                && authorization.startsWith(BEARER_HEADER);
    }

    // 유효한 Authorization Bearer Token에서 AccessToken 만 뽑아오기
    public static String getAuthorizationAccessToken(HttpServletRequest request) {
        // "Bearer " 문자열 제외하고 뽑아오기
        return request.getHeader(AUTHORIZATION).substring(7);
    }

}

