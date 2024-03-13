package synk.meeteam.domain.portfolio.portfolio.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.output.entity.Output;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.BaseTimeEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    //유저
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    //제목
    @NotNull
    @Size(max = 40)
    @Column(length = 40)
    private String title;

    //부연설명
    @NotNull
    private String description;

    //내용
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    //진행기간 시작일
    @NotNull
    private LocalDate proceedStart;

    //진행기간 종료일
    @NotNull
    private LocalDate proceedEnd;

    //분야
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "field_id")
    private Field field;

    //역할
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    //산출물
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "output_id")
    private Output output;

    //핀인지 여부
    @ColumnDefault("0")
    private Boolean isPin;

    //핀 순서
    private int pinOrder;

    public void putPin(int order) {
        isPin = true;
        pinOrder = order;
    }

    public void pullOutPin() {
        isPin = false;
    }
}
