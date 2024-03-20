package synk.meeteam.domain.user.user.entity;

import static jakarta.persistence.FetchType.EAGER;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.global.entity.BaseTimeEntity;
import synk.meeteam.global.util.Encryption;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    //이메일
    @NotNull
    @Size(max = 100)
    @Column(length = 100, updatable = false)
    private String universityEmail;

    @ColumnDefault("1")
    private boolean isPublicUniversityEmail = true;

    private String subEmail = "";

    @ColumnDefault("0")
    private boolean isPublicSubEmail = false;

    @ColumnDefault("1")
    private boolean isUniversityMainEmail = true;

    //이름
    @NotNull
    @Size(max = 20)
    @Column(length = 20)
    private String name;

    //닉네임
    @NotNull
    @NotBlank
    @Size(min = 4, max = 16)
    @Column(length = 16, unique = true)
    private String nickname;

    //비밀번호
    @Column(length = 16)
    private String password;

    //전화번호
    @Column(length = 15)
    private String phoneNumber;

    @ColumnDefault("0")
    private boolean isPublicPhone = false;

    //한줄 소개
    @Column(length = 20)
    private String oneLineIntroduction;

    //자기 소개
    @Column(columnDefinition = "TEXT")
    private String mainIntroduction;

    //학점
    private double gpa = 0.0;

    //최대학점
    private double maxGpa = 4.5;

    //입학년도
    @NotNull
    private Integer admissionYear;

    //프로필 이미지 url
    @Column(length = 300)
    private String profileImgFileName;

    //평가 점수
    @ColumnDefault("0")
    private double evaluationScore;

    //학교
    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "university_id", updatable = false)
    private University university;

    //학과
    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

    //관심있는 역할
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "interest_role_id")
    private Role interestRole;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private PlatformType platformType; // KAKAO, NAVER, GOOGLE, NONE

    private String platformId;

    //평가 점수

    @ColumnDefault("0")
    private long scoreTime = 0;


    @ColumnDefault("0")
    private long scoreInfluence = 0;


    @ColumnDefault("0")
    private long scoreParticipation = 0;


    @ColumnDefault("0")
    private long scoreCommunication = 0;


    @ColumnDefault("0")
    private long scoreProfessionalism = 0;

    public User(String platformId) {
        this.platformId = platformId;
    }

    @Builder
    public User(String universityEmail, String name, String nickname, String password, String phoneNumber,
                Integer admissionYear, String profileImgFileName, Authority authority, PlatformType platformType,
                String platformId,
                University university, Department department) {
        this.universityEmail = universityEmail;
        this.university = university;
        this.department = department;
        this.name = name;
        this.profileImgFileName = profileImgFileName;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.admissionYear = admissionYear;
        this.authority = authority;
        this.platformType = platformType;
        this.platformId = platformId;
    }

    //프로필 정보 업데이트
    public void updateProfile(
            String name,
            String profileImgFileName,
            String subEmail,
            boolean isPublicSubEmail,
            boolean isPublicSchoolEmail,
            boolean isSchoolMain,
            String phoneNumber,
            boolean isPublicPhone,
            String oneLineIntroduction,
            String mainIntroduction,
            double gpa,
            double maxGpa,
            Role role
    ) {
        this.name = name;
        this.profileImgFileName = profileImgFileName;
        this.subEmail = subEmail;
        this.isPublicSubEmail = isPublicSubEmail;
        this.isPublicUniversityEmail = isPublicSchoolEmail;
        this.isUniversityMainEmail = isSchoolMain;
        this.phoneNumber = phoneNumber;
        this.isPublicPhone = isPublicPhone;
        this.oneLineIntroduction = oneLineIntroduction;
        this.mainIntroduction = mainIntroduction;
        this.gpa = gpa;
        this.maxGpa = maxGpa;
        this.interestRole = role;
    }

    public String getEncryptUserId() {
        return Encryption.encryptLong(this.id);
    }

    public Long decryptUserId(String encryptedUserId) {
        return Encryption.decryptLong(encryptedUserId);
    }

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.authority = Authority.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }



    //닉네임 변경
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isNotEqualNickname(String nickname) {
        return !this.nickname.equals(nickname);
    }
}
