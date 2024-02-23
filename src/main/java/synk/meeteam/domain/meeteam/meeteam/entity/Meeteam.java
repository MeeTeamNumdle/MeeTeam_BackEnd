package synk.meeteam.domain.meeteam.meeteam.entity;

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
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.BaseEntity;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeteam extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeteam_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User leader;

    @NotNull
    @Column(length = 40)
    @Size(max = 40)
    private String name;

    @ColumnDefault("0")
    private long likeCount = 0L;

    //소개
    @Column(columnDefinition = "TEXT")
    private String introduction;

    //상태
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MeeteamStatus meeteamStatus;

    //구인 글 존재 여부
    @ColumnDefault("0")
    private boolean isRecruiting = false;

    //수업 여부
    @ColumnDefault("0")
    private boolean isCourse = false;

    //범위
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Scope scope;

    //유형
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Category category;

    //진행방식
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProceedType proceedType;

    //진행기간 시작일
    @NotNull
    private LocalDate proceedingStart;

    //진행기간 종료일
    @NotNull
    private LocalDate proceedingEnd;

    //공개 여부
    @ColumnDefault("1")
    private boolean isPublic = true;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "field_id")
    private Field field;
}
