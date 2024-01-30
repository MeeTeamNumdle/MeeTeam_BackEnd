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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import synk.meeteam.domain.base.entity.BaseEntity;
import synk.meeteam.domain.field.entity.Field;
import synk.meeteam.domain.user.entity.User;

@Getter
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
    @Column(length = 50)
    @Size(max = 50)
    private String name;

    @NotNull
    @ColumnDefault("0")
    private Long likeCount = 0L;

    //소개
    @Column(columnDefinition = "TEXT")
    private String introduction;

    //상태
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MeeteamStatus meeteamStatus;

    //구인 글 존재 여부
    @NotNull
    @ColumnDefault("0")
    private Boolean isRecruiting = false;

    //수업 여부
    @NotNull
    @ColumnDefault("0")
    private Boolean isCourse = false;

    //범위
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MeeteamScope meeteamScope;

    // 분야
    @NotNull
    @Enumerated(EnumType.STRING)
    private MeeteamCategory meeteamCategory;

    // 진행 방식
    @NotNull
    @Enumerated(EnumType.STRING)
    private MeeteamProceed meeteamProceed;

    //기간 시작일
    @NotNull
    private LocalDate proceedingStart;

    //기간 종료일
    @NotNull
    private LocalDate proceedingEnd;

    //공개 여부
    @NotNull
    @ColumnDefault("1")
    private Boolean isPublic = true;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    @NotNull
    private String coverImageUrl;

    @Builder
    public Meeteam(final User leader, final String name, final Long likeCount, final String introduction, final MeeteamStatus meeteamStatus,
                   final Boolean isRecruiting, final Boolean isCourse, final MeeteamScope meeteamScope, final MeeteamCategory meeteamCategory,
                   final MeeteamProceed meeteamProceed, final LocalDate proceedingStart, final LocalDate proceedingEnd,
                   final Boolean isPublic, final Field field, final String coverImageUrl) {
        this.leader = leader;
        this.name = name;
        this.likeCount = likeCount;
        this.introduction = introduction;
        this.meeteamStatus = meeteamStatus;
        this.isRecruiting = isRecruiting;
        this.isCourse = isCourse;
        this.meeteamScope = meeteamScope;
        this.meeteamCategory = meeteamCategory;
        this.meeteamProceed = meeteamProceed;
        this.proceedingStart = proceedingStart;
        this.proceedingEnd = proceedingEnd;
        this.isPublic = isPublic;
        this.field = field;
        this.coverImageUrl = coverImageUrl;
    }
}
