package synk.meeteam.security.jwt.service.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@NoArgsConstructor
@RedisHash(value = "refresh", timeToLive = 604800016)
public class TokenVO {
    @Id
    @Indexed
    private String platformId;

    private boolean isBlack;

    private String refreshToken;


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateBlack(boolean black) {
        this.isBlack = black;
    }

    @Builder
    public TokenVO(String platformId, boolean isBlack, String refreshToken) {
        this.platformId = platformId;
        this.isBlack = isBlack;
        this.refreshToken = refreshToken;
    }
}
