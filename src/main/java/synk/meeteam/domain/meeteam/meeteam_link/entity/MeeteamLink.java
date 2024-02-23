package synk.meeteam.domain.meeteam.meeteam_link.entity;

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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.meeteam.meeteam.entity.Meeteam;
import synk.meeteam.global.entity.BaseTimeEntity;
import synk.meeteam.global.entity.LinkType;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeeteamLink extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeteam_link_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "meeteam_id")
    private Meeteam meeteam;

    @NotNull
    @Column(length = 500)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private LinkType type;
}
