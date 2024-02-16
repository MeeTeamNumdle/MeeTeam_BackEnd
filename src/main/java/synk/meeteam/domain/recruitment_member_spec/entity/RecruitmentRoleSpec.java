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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.spec.entity.Spec;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "recruitment_role_spec_uk",
                columnNames = {"recruitment_role_id", "spec_id"}
        )
})
public class RecruitmentRoleSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_role_spec_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_role_id")
    private RecruitmentRole recruitmentRole;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "spec_id")
    private Spec spec;
}
