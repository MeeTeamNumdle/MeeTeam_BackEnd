package synk.meeteam.domain.recruitment.recruitment_comment.entity;

import static jakarta.persistence.FetchType.LAZY;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.BaseTimeEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class RecruitmentComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_post_id")
    private RecruitmentPost recruitmentPost;

    //작성자
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "writer_id")
    private User writer;

    //내용
    @NotNull
    @Size(max = 100)
    @Column(length = 100)
    private String comment;

    //댓글, 대댓글 구분
    @NotNull
    private Boolean isParent;

    //그룹 번호
    @NotNull
    private Long group_number;

    /*
    순서
    단, 댓글 내에서의 정렬
     */
    @NotNull
    private Long order;

    @NotNull
    @ColumnDefault("0")
    private Boolean isDeleted;
}
