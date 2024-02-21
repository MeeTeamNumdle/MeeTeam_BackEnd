package synk.meeteam.domain.user.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;

@Getter
@NoArgsConstructor
@RedisHash(value = "user", timeToLive = 60480000)
public class UserVO {

    @Id
    @Indexed
    private String platformId;

    private String email;

    private String name;

    private String phoneNumber;

    private PlatformType platformType;

    private Long universityId;

    private Long departmentId;

    private int admissionYear;

    private String pictureUrl;

    @Indexed
    private String emailCode;

    @Builder
    public UserVO(String platformId, String email, String name, String phoneNumber, PlatformType platformType,
                  String pictureUrl) {
        this.platformId = platformId;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.platformType = platformType;
        this.pictureUrl = pictureUrl;
    }

    public void updateUniversityInfo(Long universityId, Long departmentId, int admissionYear, String email){
        this.universityId = universityId;
        this.departmentId = departmentId;
        this.admissionYear = admissionYear;
        this.email = email;
    }

    public void updateEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

}
