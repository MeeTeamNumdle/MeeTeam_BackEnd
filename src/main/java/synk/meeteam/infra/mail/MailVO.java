package synk.meeteam.infra.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import synk.meeteam.domain.user.entity.enums.PlatformType;

@Getter
@NoArgsConstructor
@RedisHash(value = "mail", timeToLive = 600)
public class MailVO {
    @Id
    @Indexed
    private String emailCode;

    private String email;

    private String platformId;

    private PlatformType platformType;

    @Builder
    public MailVO(String emailCode, String email, String platformId, PlatformType platformType) {
        this.emailCode = emailCode;
        this.email = email;
        this.platformId = platformId;
        this.platformType = platformType;
    }
}
