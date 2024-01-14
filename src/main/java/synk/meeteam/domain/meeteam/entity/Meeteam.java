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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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
    private MeeteamStatus meeteamStatus;

    //구인 글 존재 여부
    @NotNull
    @ColumnDefault("0")
    private Boolean isRecruiting = false;

    //기간 시작일
    @NotNull
    private LocalDateTime proceedingStart;

    //기간 종료일
    @NotNull
    private LocalDateTime proceedingEnd;

    //공개 여부
    @NotNull
    @ColumnDefault("1")
    private Boolean isPublic = true;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "field_id")
    private Field field;
}
