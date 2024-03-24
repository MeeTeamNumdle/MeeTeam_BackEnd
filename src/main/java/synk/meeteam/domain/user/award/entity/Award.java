package synk.meeteam.domain.user.award.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import synk.meeteam.global.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.MODULE)
@Builder
public class Award extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "award_id")
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private LocalDate proceedingStart;

    @NotNull
    private LocalDate proceedingEnd;

    public Award(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
