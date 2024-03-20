package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import synk.meeteam.domain.common.tag.dto.TagDto;

@Schema(name = "GetTagDto", description = "구인태그 조회 Dto")
public record GetTagDto(
        @Schema(description = "구인 태그 id", example = "1")
        Long id,
        @Schema(description = "구인 태그 이름", example = "대학생")
        String name
) {
    @Builder
    @QueryProjection
    public GetTagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GetTagDto from(TagDto tag) {
        return new GetTagDto(tag.id(), tag.name());
    }
}
