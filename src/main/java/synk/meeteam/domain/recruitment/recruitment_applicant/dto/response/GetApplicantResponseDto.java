package synk.meeteam.domain.recruitment.recruitment_applicant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import synk.meeteam.global.dto.SliceInfo;


@Schema(name = "GetApplicantResponseDto", description = "신청자 조회 Dto")
public record GetApplicantResponseDto(
        @Schema(description = "신청 id", example = "1")
        List<GetApplicantDto> applicants,
        SliceInfo pageInfo
) {

}
