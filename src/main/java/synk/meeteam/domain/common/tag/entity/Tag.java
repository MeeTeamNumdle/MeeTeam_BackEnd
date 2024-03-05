package synk.meeteam.domain.common.tag.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import synk.meeteam.global.entity.BaseEntity;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @NotNull
    @Column(length = 15)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TagType type = TagType.MEETEAM;

    @Builder
    public Tag(String name, TagType type) {
        this.name = name;
        this.type = type;
    }
}