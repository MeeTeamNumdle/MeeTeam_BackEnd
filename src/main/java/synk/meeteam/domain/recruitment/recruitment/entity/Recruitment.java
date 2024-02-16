package synk.meeteam.domain.recruitment.recruitment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.global.entity.BaseEntity;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    //제목
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
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
}
