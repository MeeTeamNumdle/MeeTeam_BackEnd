package synk.meeteam.security.jwt.service;



import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_ACCESS_TOKEN;
import static synk.meeteam.domain.auth.exception.AuthExceptionType.INVALID_REFRESH_TOKEN;
import static synk.meeteam.domain.auth.exception.AuthExceptionType.UNAUTHORIZED_ACCESS_TOKEN;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.auth.api.dto.response.MemberAuthResponseDTO;
import synk.meeteam.domain.auth.api.dto.response.MemberReissueResponseDTO;
import synk.meeteam.domain.auth.exception.AuthException;
import synk.meeteam.domain.auth.exception.AuthExceptionType;
import synk.meeteam.domain.auth.service.vo.UserSignUpVO;
import synk.meeteam.domain.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.entity.enums.Role;
import synk.meeteam.domain.user.repository.UserRepository;
import synk.meeteam.infra.redis.repository.RedisTokenRepository;
import synk.meeteam.security.jwt.service.vo.TokenVO;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
@Transactional(readOnly = true)
public class JwtService {
    private static final String AUTH_USER = "memberId";
    private static final String BEARER = "Bearer ";
    private static final String EMAIL_CLAIM = "email";
    private static final String PLATFORM_ID_CLAIM = "platformId";
    private static final String PLATFORM_TYPE_CLAIM = "platformType";



    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenRepository redisTokenRepository;

    @Transactional
    public MemberAuthResponseDTO issueToken(UserSignUpVO vo) {
        String accessToken = jwtTokenProvider.createAccessToken(vo.platformId(), vo.platformType(), accessTokenExpirationPeriod);

        if (vo.role().equals(Role.USER)) {
            String refreshToken = jwtTokenProvider.createRefreshToken(refreshTokenExpirationPeriod);
            updateRefreshTokenByMemberId(vo.platformId(), refreshToken);
            return MemberAuthResponseDTO.of(vo.platformId(), vo.authType(), vo.name(), Role.USER, accessToken, refreshToken);
        }

        throw new AuthException(AuthExceptionType.UNAUTHORIZED_MEMBER_LOGIN);
    }

    public MemberReissueResponseDTO reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);
        String accessToken = extractAccessToken(request);

        if (!validateToken(refreshToken)) {
            throw new AuthException(INVALID_REFRESH_TOKEN);
        }

        Claims tokenClaims = jwtTokenProvider.getTokenClaims(accessToken);
        TokenVO foundRefreshToken = redisTokenRepository.findByPlatformIdOrElseThrowException(
                String.valueOf(tokenClaims.get(PLATFORM_ID_CLAIM)));

        if (!foundRefreshToken.getRefreshToken().equals(refreshToken)) {
            throw new AuthException(INVALID_REFRESH_TOKEN);
        }

        String platformId = (String) tokenClaims.get(PLATFORM_ID_CLAIM);
        PlatformType platformType = (PlatformType) tokenClaims.get(PLATFORM_TYPE_CLAIM);

        String newAccessToken = jwtTokenProvider.createAccessToken(platformId, platformType, accessTokenExpirationPeriod);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(refreshTokenExpirationPeriod);

        updateRefreshTokenByMemberId(platformId, newRefreshToken);

        return MemberReissueResponseDTO.of(platformId, newAccessToken, newRefreshToken);
    }


    public String extractPlatformIdFromAccessToken(final String atk) throws JsonProcessingException {
        Claims tokenClaims = jwtTokenProvider.getTokenClaims(atk);
        return jwtTokenProvider.getPlatformIdFromClaim(tokenClaims, PLATFORM_ID_CLAIM);
    }

    public Boolean validateToken(final String atk) {
        try {
            Claims tokenClaims = jwtTokenProvider.getTokenClaims(atk);
            return !tokenClaims.getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            throw new AuthException(INVALID_ACCESS_TOKEN);
        } catch (ExpiredJwtException e){
            throw new AuthException(UNAUTHORIZED_ACCESS_TOKEN);
        }
    }

    private String extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""))
                .orElseThrow(() -> new AuthException(INVALID_REFRESH_TOKEN));
    }

    private String extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""))
                .orElseThrow(() -> new AuthException(INVALID_ACCESS_TOKEN));
    }


    @Transactional
    public void updateRefreshTokenByMemberId(String platformId, String newRefreshToken) {
        redisTokenRepository.findByPlatformId(String.valueOf(platformId))
                .ifPresent(refreshToken -> {
                    refreshToken.updateBlack(true);
                });
        log.info("newRefreshToken = {}", newRefreshToken);
        redisTokenRepository.save(TokenVO.builder()
                .platformId(platformId)
                .black(false)
                .refreshToken(newRefreshToken)
                .build());
    }
}