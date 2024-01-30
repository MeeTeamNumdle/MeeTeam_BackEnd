package synk.meeteam.domain.recruitment.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.base.entity.BaseEntity;
import synk.meeteam.domain.meeteam.entity.Meeteam;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    @OneToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "meeteam_id")
    private Meeteam meeteam;

    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private LocalDateTime deadline;

    @Builder
    public Recruitment(final Meeteam meeteam, final String title, final String content, final LocalDateTime deadline) {
        this.meeteam = meeteam;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
    }
}
