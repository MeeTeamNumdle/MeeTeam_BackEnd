package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TagDto", description = "구인태그 Dto")
public record TagDto(
        @Schema(description = "구인 태그 id", example = "1")
        Long id,
        @Schema(description = "구인 태그 이름", example = "대학생")
        String name
) {
}
