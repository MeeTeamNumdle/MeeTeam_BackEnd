package synk.meeteam.domain.recruitment_member.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.role.entity.Role;
import synk.meeteam.domain.recruitment.entity.Recruitment;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_member_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    @NotNull
    private int count;

    @Builder
    public RecruitmentMember(Recruitment recruitment, Role role, int count) {
        this.recruitment = recruitment;
        this.role = role;
        this.count = count;
    }
}
