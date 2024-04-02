package synk.meeteam.domain.recruitment.recruitment_comment.entity;

import static jakarta.persistence.FetchType.LAZY;
import static synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentExceptionType.INVALID_USER;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import synk.meeteam.domain.recruitment.recruitment_comment.exception.RecruitmentCommentException;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.global.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_post_id")
    private RecruitmentPost recruitmentPost;

    //내용
    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100)
    private String content;

    //댓글, 대댓글 구분
    @ColumnDefault("1")
    private boolean isParent = true;

    //그룹 번호
    private long groupNumber;

    //그룹 내 순서
    private long groupOrder;

    @ColumnDefault("0")
    private boolean isDeleted = false;

    @Builder
    public RecruitmentComment(RecruitmentPost recruitmentPost, String content, boolean isParent, long groupNumber,
                              long groupOrder, boolean isDeleted) {
        this.recruitmentPost = recruitmentPost;
        this.content = content;
        this.isParent = isParent;
        this.groupNumber = groupNumber;
        this.groupOrder = groupOrder;
        this.isDeleted = isDeleted;
    }

    public void updateGroupNumberAndGroupOrder(long groupNumber, long groupOrder) {
        this.groupNumber = groupNumber;
        this.groupOrder = groupOrder;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public void validateWriter(Long userId) {
        if (!this.getCreatedBy().equals(userId)) {
            throw new RecruitmentCommentException(INVALID_USER);
        }
    }
}
