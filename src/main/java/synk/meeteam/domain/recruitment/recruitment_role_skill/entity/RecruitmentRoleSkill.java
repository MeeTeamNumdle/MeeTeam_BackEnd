package synk.meeteam.domain.recruitment.recruitment_role_skill.entity;

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
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "recruitment_role_skill_uk",
                columnNames = {"recruitment_role_id", "skill_id"}
        )
})
public class RecruitmentRoleSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_role_skill_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_role_id")
    private RecruitmentRole recruitmentRole;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Builder
    public RecruitmentRoleSkill(RecruitmentRole recruitmentRole, Skill skill) {
        this.recruitmentRole = recruitmentRole;
        this.skill = skill;
    }
}
