package synk.meeteam.domain.timetable_block.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.DayOfWeek;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.user.entity.User;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimetableBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timetable_block_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private DayOfWeek dayOfWeek;

    @NotNull
    @Size(max = 5)
    @Column(length = 5)
    private String startTime;

    @NotNull
    @Size(max = 5)
    @Column(length = 5)
    private String endTime;
}
