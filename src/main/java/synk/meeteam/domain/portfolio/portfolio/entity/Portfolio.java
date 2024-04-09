package synk.meeteam.domain.portfolio.portfolio.entity;

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
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.global.entity.BaseEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.MODULE)
@Builder
public class Portfolio extends BaseEntity {

    public static int MAX_PIN_SIZE = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

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

    //포트폴리오 커버 이미지
    @NotNull
    private String mainImageExtension;

    //핀인지 여부
    @ColumnDefault("0")
    private Boolean isPin;

    //핀 순서
    @ColumnDefault("0")
    private int pinOrder;

    @Builder
    public Portfolio(Long id, String title, String description, Boolean isPin, int pinOrder) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPin = isPin;
        this.pinOrder = pinOrder;
    }

    public void putPin(int order) {
        isPin = true;
        pinOrder = order;
    }

    public void unpin() {
        isPin = false;
        pinOrder = 0;
    }
}
