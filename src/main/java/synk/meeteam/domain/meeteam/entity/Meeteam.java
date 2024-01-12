package synk.meeteam.domain.meeteam.entity;

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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.field.entity.Field;
import synk.meeteam.domain.user.entity.User;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeteam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeteam_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User leader;

    private Long likeCount;
    private String name;

    //소개
    //상태
    @Enumerated(EnumType.STRING)
    private MeeteamStatus meeteamStatus;

    //구인 글 존재 여부
    private Boolean isRecruiting;

    //기간 시작일
    private LocalDateTime proceedingStart;

    //기간 종료일
    private LocalDateTime proceedingEnd;

    //공개 여부
    private Boolean isPublic;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "field_id")
    private Field field;
}
