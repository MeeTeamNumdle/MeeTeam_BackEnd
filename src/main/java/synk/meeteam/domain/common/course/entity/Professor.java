package synk.meeteam.domain.common.course.entity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synk.meeteam.domain.common.university.entity.University;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Long id;

    @NotNull
    @Column(length = 20)
    private String name;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "university_id")
    private University university;
}
