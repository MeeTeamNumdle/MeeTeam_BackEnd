package synk.meeteam.domain.recruitment.recruitment_applicant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetRecruitmentRoleStatusResponseDto", description = "구인 현황 조회 Dto")
public record GetRecruitmentRoleStatusResponseDto(
        @Schema(description = "역할 이름", example = "기획자")
        String roleName,
        @Schema(description = "모집 인원", example = "3")
        long recruitMemberCount,
        @Schema(description = "승인 인원", example = "1")
        long approvedMemberCount
) {
}
