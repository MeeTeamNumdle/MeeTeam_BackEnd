package synk.meeteam.domain.common.university.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "GetUniversityListDto", description = "대학교 목록 조회 응답 Dto")
public record GetUniversityListDto(
        @Schema(description = "대학교 목록", example = "")
        List<GetUniversityDto> universityList
) {
}
