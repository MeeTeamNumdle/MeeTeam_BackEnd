package synk.meeteam.domain.user.user.entity;

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
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.global.entity.BaseTimeEntity;




@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@DynamicInsert
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    //이메일
    @NotNull
    @Size(max = 100)
    @Column(length = 100)
    private String email;

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
    @Column(length = 11)
    private String phoneNumber;

    //한줄 소개
    @Column(length = 20)
    private String oneLineIntroduction;

    //자기 소개
    @Column(columnDefinition = "TEXT")
    private String mainIntroduction;

    //학점
    private Double gpa;

    //입학년도
    @NotNull
    private Integer admissionYear;

    //프로필 이미지 url
    @Column(length = 300)
    private String pictureUrl;

    //평가 점수
    @NotNull
    @ColumnDefault("0")
    private Double evaluationScore;

    //학교
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "university_id")
    private University university;

    //학과
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

    //관심있는 역할
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "interest_role_id")
    private Role interestRole;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private PlatformType platformType; // KAKAO, NAVER, GOOGLE, NONE

    private String platformId;

    //평가 점수
    @NotNull
    @ColumnDefault("0")
    private Long scoreTime;

    @NotNull
    @ColumnDefault("0")
    private Long scoreInfluence;

    @NotNull
    @ColumnDefault("0")
    private Long scoreParticipation;

    @NotNull
    @ColumnDefault("0")
    private Long scoreCommunication;

    @NotNull
    @ColumnDefault("0")
    private Long scoreProfessionalism;


    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public void updateUniversity(University university){
        this.university = university;
    }

    @Builder
    public User(String email, String name, String nickname, String password, String phoneNumber,
                Integer admissionYear, Authority authority, PlatformType platformType, String platformId,
                University university) {
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

    public void updateAuthority(Authority authority) {
        this.authority = authority;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.authority = Authority.USER;
    }
}
