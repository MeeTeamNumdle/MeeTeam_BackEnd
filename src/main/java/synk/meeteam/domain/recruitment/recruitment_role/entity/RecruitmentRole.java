package synk.meeteam.domain.recruitment.recruitment_role.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_role_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "recruitment_id")
    private RecruitmentPost recruitmentPost;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    @ColumnDefault("0")
    private long count = 0L;  // 구인하는 인원

    @ColumnDefault("0")
    private long applicantCount = 0L;  // 지원한 인원

    @ColumnDefault("0")
    private long recruitedCount = 0L;  // 구인된 인원

    @OneToMany(mappedBy = "recruitmentRole")
    private List<RecruitmentRoleSkill> recruitmentRoleSkills = new ArrayList<>();

    @Builder
    public RecruitmentRole(RecruitmentPost recruitmentPost, Role role, long count) {
        this.recruitmentPost = recruitmentPost;
        this.role = role;
        this.count = count;
    }

    public void addApplicantCount() {
        this.applicantCount += 1;
    }
}
