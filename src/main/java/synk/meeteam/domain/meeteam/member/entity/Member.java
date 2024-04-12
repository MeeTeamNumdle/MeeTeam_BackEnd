package synk.meeteam.domain.meeteam.member.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.meeteam.meeteam.entity.Meeteam;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.BaseTimeEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "member_uk",
                columnNames = {"user_id", "meeteam_id"}
        )
})
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "meeteam_id")
    private Meeteam meeteam;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;
}
