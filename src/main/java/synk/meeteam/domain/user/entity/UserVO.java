package synk.meeteam.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import synk.meeteam.domain.user.entity.enums.PlatformType;

@Getter
@NoArgsConstructor
@RedisHash(value = "user", timeToLive = 604800016)
public class UserVO {

    @Id
    @Indexed
    private String platformId;

    private String email;

    private String name;

    private String phoneNumber;

    private PlatformType platformType;

    private Long universityId;

    private int admissionYear;

    @Indexed
    private String emailCode;

    @Builder
    public UserVO(String platformId, String email, String name, String phoneNumber, PlatformType platformType) {
        this.platformId = platformId;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.platformType = platformType;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public void updateAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public void updateEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

}
