package synk.meeteam.domain.user.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import synk.meeteam.domain.base.entity.BaseTimeEntity;
import synk.meeteam.domain.university.entity.University;
import synk.meeteam.domain.user.entity.enums.Authority;
import synk.meeteam.domain.user.entity.enums.PlatformType;




@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(length = 100)
    private String email;

    @NotNull
    @Size(max = 20)
    @Column(length = 20)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(length = 20, unique = true)
    private String nickname;

    @Column(length = 100)
    private String password;

    @Column(length = 15)
    private String phoneNumber;

    //한줄 소개
    @Column(length = 20)
    private String oneLineIntroduction;

    //자기 소개
    @Column(columnDefinition = "TEXT")
    private String mainIntroduction;

    private Double gpa;

    @NotNull
    private Integer admissionYear;

    @Column(length = 300)
    private String pictureUrl;

    //평가 점수
    private String evaluationScore;

    //학사 정보
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "university_id")
    private University university;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private PlatformType platformType; // KAKAO, NAVER, GOOGLE, NONE

    private String platformId;


    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public void updateUniversity(University university){
        this.university = university;
    }

    public void updateRole(Authority authority){
        this.authority = authority;
    }

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.authority = Authority.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    @Builder
    public User(String email, String name, String nickname, String password, String phoneNumber,
                Integer admissionYear, Authority authority, PlatformType platformType, String platformId) {
        this.email = email;
        this.university = university;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.admissionYear = admissionYear;
        this.authority = authority;
        this.platformType = platformType;
        this.platformId = platformId;
    }
}
