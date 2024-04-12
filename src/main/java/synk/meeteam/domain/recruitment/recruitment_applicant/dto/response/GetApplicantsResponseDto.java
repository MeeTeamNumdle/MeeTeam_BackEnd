package synk.meeteam.domain.recruitment.recruitment_applicant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import synk.meeteam.global.dto.PageInfo;

@Schema(name = "GetApplicantsResponseDto", description = "신청자 목록 조회 Dto")
public record GetApplicantsResponseDto(
        @Schema(description = "신청자 정보", example = "")
        List<GetApplicantResponseDto> applicants,
        @Schema(description = "페이지 정보", example = "")
        PageInfo pageInfo
) {
}
