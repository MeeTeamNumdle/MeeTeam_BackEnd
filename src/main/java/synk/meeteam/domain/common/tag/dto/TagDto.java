package synk.meeteam.domain.common.tag.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import synk.meeteam.domain.common.tag.entity.TagType;

@Schema(name = "TagDto", description = "구인태그 Dto")
public record TagDto(
        @Schema(description = "구인 태그 id", example = "1")
        Long id,
        @Schema(description = "구인 태그 이름", example = "대학생")
        String name,
        @Schema(description = "구인 태그 종류", example = "MEETEAM")
        TagType type
) {
    @Builder
    @QueryProjection
    public TagDto(Long id, String name, TagType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public TagDto(String name, TagType type) {
        this(null, name, type);
    }
}
