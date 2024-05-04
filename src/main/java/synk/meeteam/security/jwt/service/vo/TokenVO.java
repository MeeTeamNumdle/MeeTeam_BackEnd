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
    private String userId;

    private boolean isBlack;

    private String refreshToken;


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateBlack(boolean black) {
        this.isBlack = black;
    }

    @Builder
    public TokenVO(String userId, boolean isBlack, String refreshToken) {
        this.userId = userId;
        this.isBlack = isBlack;
        this.refreshToken = refreshToken;
    }
}
