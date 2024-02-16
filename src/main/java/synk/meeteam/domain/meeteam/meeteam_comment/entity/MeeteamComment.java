package synk.meeteam.domain.meeteam.meeteam_comment.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.meeteam.meeteam.entity.Meeteam;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.BaseTimeEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeeteamComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeteam_comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "meeteam_id")
    private Meeteam meeteam;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "writer_id")
    private User writer;

    @NotNull
    @Size(max = 100)
    @Column(length = 100)
    private String content;
}
