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

    private boolean black;

    private String refreshToken;


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateBlack(boolean black) {
        this.black = black;
    }

    @Builder
    public TokenVO(String platformId, boolean black, String refreshToken) {
        this.platformId = platformId;
        this.black = black;
        this.refreshToken = refreshToken;
    }
}
