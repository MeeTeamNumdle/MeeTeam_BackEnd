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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.base.entity.BaseTimeEntity;
import synk.meeteam.domain.university.entity.University;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(length = 11)
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

    //계정 타입
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private UserType type;

    //학사 정보
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "university_id")
    private University university;
}
