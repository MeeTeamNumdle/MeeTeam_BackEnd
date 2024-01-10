package synk.meeteam.domain.recruitment_member_spec.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.spec.entity.Spec;
import synk.meeteam.domain.recruitment_member.entity.RecruitmentMember;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentMemberSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_member_spec_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recruitment_member_id")
    private RecruitmentMember recruitmentMember;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spec_id")
    private Spec spec;
}
