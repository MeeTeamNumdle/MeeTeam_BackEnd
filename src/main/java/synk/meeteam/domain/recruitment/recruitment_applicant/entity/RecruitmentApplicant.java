package synk.meeteam.domain.recruitment.recruitment_applicant.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.BaseTimeEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "RecruitmentApplicant_uk",
                columnNames = {"recruitment_id", "applicant_id"}
        )
})
public class RecruitmentApplicant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_applicant_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_id")
    private RecruitmentPost recruitmentPost;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "applicant_id")
    private User applicant;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    //전할 말
    @Column(length = 300)
    private String comment;

    @Builder
    public RecruitmentApplicant(RecruitmentPost recruitmentPost, User applicant, Role role, String comment) {
        this.recruitmentPost = recruitmentPost;
        this.applicant = applicant;
        this.role = role;
        this.comment = comment;
    }
}
