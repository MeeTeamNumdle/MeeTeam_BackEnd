package synk.meeteam.domain.recruitment.recruitment_applicant.entity;

import static jakarta.persistence.FetchType.LAZY;
import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.ALREADY_PROCESSED_APPLICANT;
import static synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantExceptionType.INVALID_USER;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_applicant.exception.RecruitmentApplicantException;
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
                columnNames = {"recruitment_post_id", "applicant_id"}
        )
})
public class RecruitmentApplicant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_applicant_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_post_id")
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;

    @Builder
    public RecruitmentApplicant(RecruitmentPost recruitmentPost, User applicant, Role role, String comment) {
        this.recruitmentPost = recruitmentPost;
        this.applicant = applicant;
        this.role = role;
        this.comment = comment;
    }

    public void validateCanApprove(Long userId) {
        validateWriter(userId);

        if (!recruitStatus.equals(RecruitStatus.NONE)) {
            throw new RecruitmentApplicantException(ALREADY_PROCESSED_APPLICANT);
        }
    }

    private void validateWriter(Long userId) {
        if (!this.recruitmentPost.getCreatedBy().equals(userId)) {
            throw new RecruitmentApplicantException(INVALID_USER);
        }
    }
}
