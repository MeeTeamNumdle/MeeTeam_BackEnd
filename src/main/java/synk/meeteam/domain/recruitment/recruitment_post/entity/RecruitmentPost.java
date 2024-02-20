package synk.meeteam.domain.recruitment.recruitment_post.entity;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.meeteam.meeteam.entity.Meeteam;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.BaseTimeEntity;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class RecruitmentPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    //작성자
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "writer_id")
    private User writer;

    //제목
    @NotNull
    @Size(max = 40)
    @Column(length = 40)
    private String title;

    //내용
    @Column(columnDefinition = "TEXT")
    private String content;

    //범위
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Scope scope;

    //유형
    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    //분야
    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "field_id")
    private Field field;

    //진행방식
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProceedType proceedType;

    //진행기간 시작일
    @NotNull
    private LocalDate proceedingStart;

    //진행기간 종료일
    @NotNull
    private LocalDate proceedingEnd;

    //마감일
    @NotNull
    private LocalDate deadline;

    //북마크 수
    private long bookmarkCount = 0L;

    //카카오톡 링크
    @Column(length = 300)
    private String kakaoLink;

    //마감여부 저장
    @NotNull
    @ColumnDefault("0")
    private Boolean isClosed;

    //밋팀
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "meeteam_id")
    private Meeteam meeteam;

}
