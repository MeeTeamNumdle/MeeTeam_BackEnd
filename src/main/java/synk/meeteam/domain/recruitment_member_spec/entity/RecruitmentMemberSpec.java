package synk.meeteam.domain.recruitment_member_spec.entity;

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
import synk.meeteam.domain.spec.entity.Spec;
import synk.meeteam.domain.recruitment_member.entity.RecruitmentMember;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "recruitment_member_uk",
                columnNames = {"recruitment_member_id", "spec_id"}
        )
})
public class RecruitmentMemberSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_member_spec_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_member_id")
    private RecruitmentMember recruitmentMember;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "spec_id")
    private Spec spec;

    @Builder
    public RecruitmentMemberSpec(RecruitmentMember recruitmentMember, Spec spec) {
        this.recruitmentMember = recruitmentMember;
        this.spec = spec;
    }
}
