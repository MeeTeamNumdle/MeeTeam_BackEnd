package synk.meeteam.domain.recruitment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment_member.entity.RecruitmentMember;
import synk.meeteam.domain.meeteam.entity.Meeteam;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "meeteam_id")
    private Meeteam meeteam;

    @OneToMany(mappedBy = "recruitment")
    private List<RecruitmentMember> recruitmentMembers = new ArrayList<>();

    private String title;

    private String content;

    private Boolean isOnline;

    private LocalDateTime deadline;

    @OneToMany(mappedBy = "recruitment")
    private List<RecruitmentApplicant> applicants = new ArrayList<>();
}
