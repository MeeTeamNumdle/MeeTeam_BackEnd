package synk.meeteam.security.jwt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;

@Slf4j
@Component
public class JwtTokenProvider {
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String PLATFORM_ID_CLAIM = "platformId";
    private static final String PLATFORM_TYPE_CLAIM = "platformType";



    private final Key key;
    private final ObjectMapper objectMapper;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey, ObjectMapper objectMapper) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.objectMapper = objectMapper;
    }

    public String createAccessToken(String platformId, PlatformType platformType, Long expirationTime) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .claim(PLATFORM_ID_CLAIM, platformId)
                .claim(PLATFORM_TYPE_CLAIM, platformType)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + expirationTime))  //토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(Long expirationTime) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + expirationTime))  //토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public Claims getTokenClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims makeInfoToClaim(String infoName, final Long memberId) throws JsonProcessingException {
        String claimValue = objectMapper.writeValueAsString(memberId);
        Claims claims = Jwts.claims();
        claims.put(infoName, claimValue);
        return claims;
    }

    public String getPlatformIdFromClaim(Claims claims, String infoName) throws JsonProcessingException {

        return claims.get(infoName, String.class);
    }
}
