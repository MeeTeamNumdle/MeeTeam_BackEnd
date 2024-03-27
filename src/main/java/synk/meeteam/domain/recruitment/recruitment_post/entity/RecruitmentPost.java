package synk.meeteam.domain.recruitment.recruitment_post.entity;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static synk.meeteam.domain.recruitment.recruitment_post.exception.RecruitmentPostExceptionType.INVALID_USER_ID;

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
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.meeteam.meeteam.entity.Meeteam;
import synk.meeteam.domain.recruitment.recruitment_post.exception.RecruitmentPostException;
import synk.meeteam.global.entity.BaseEntity;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class RecruitmentPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_post_id")
    private Long id;

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
    @ColumnDefault("0")
    private boolean isClosed = false;

    //밋팀
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "meeteam_id")
    private Meeteam meeteam;

    // 전체 지원 수
    private long applicantCount = 0L;

    // 응답 횟수
    private long responseCount = 0L;


    @Builder
    public RecruitmentPost(String title, String content, Scope scope, Category category, Field field,
                           ProceedType proceedType, LocalDate proceedingStart, LocalDate proceedingEnd,
                           LocalDate deadline,
                           long bookmarkCount, String kakaoLink, boolean isClosed, Meeteam meeteam) {
        this.title = title;
        this.content = content;
        this.scope = scope;
        this.category = category;
        this.field = field;
        this.proceedType = proceedType;
        this.proceedingStart = proceedingStart;
        this.proceedingEnd = proceedingEnd;
        this.deadline = deadline;
        this.bookmarkCount = bookmarkCount;
        this.kakaoLink = kakaoLink;
        this.isClosed = isClosed;
        this.meeteam = meeteam;
    }

    public double getResponseRate() {
        if (applicantCount == 0) {
            return 0;
        }
        return ((double) responseCount / applicantCount) * 100;
    }

    public void addApplicantCount() {
        this.applicantCount += 1;
    }

    private void validateWriter(Long userId) {
        if (!this.getCreatedBy().equals(userId)) {
            throw new RecruitmentPostException(INVALID_USER_ID);
        }
    }

    public RecruitmentPost closeRecruitmentPost(Long userId) {
        // 작성자인지 확인
        validateWriter(userId);

        this.isClosed = true;
        return this;
    }

    public void updateRecruitmentPost(String title, String content, Scope scope, Category category,
                                      Field field,
                                      ProceedType proceedType, LocalDate proceedingStart,
                                      LocalDate proceedingEnd,
                                      LocalDate deadline,
                                      long bookmarkCount, String kakaoLink, boolean isClosed,
                                      Meeteam meeteam, long applicantCount,
                                      long responseCount) {
        this.title = title;
        this.content = content;
        this.scope = scope;
        this.category = category;
        this.field = field;
        this.proceedType = proceedType;
        this.proceedingStart = proceedingStart;
        this.proceedingEnd = proceedingEnd;
        this.deadline = deadline;
        this.bookmarkCount = bookmarkCount;
        this.kakaoLink = kakaoLink;
        this.isClosed = isClosed;
        this.meeteam = meeteam;
        this.applicantCount = applicantCount;
        this.responseCount = responseCount;
    }

    public RecruitmentPost incrementBookmarkCount() {
        this.bookmarkCount += 1;
        return this;
    }
}
