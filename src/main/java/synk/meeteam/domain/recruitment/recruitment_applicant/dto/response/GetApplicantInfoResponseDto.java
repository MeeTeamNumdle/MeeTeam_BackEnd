package synk.meeteam.domain.recruitment.recruitment_applicant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "GetApplicantInfoResponseDto", description = "신청 정보 조회 Dto")
public record GetApplicantInfoResponseDto(
        @Schema(description = "구인글 제목", example = "구인글 제목입니다.")
        String title,
        @Schema(description = "오픈카톡방 링크", example = "https://open.kakao.com/o/gLmqdijg")
        String link,
        @Schema(description = "처음 접속 여부", example = "true")
        boolean isFirstAccess,
        List<GetRecruitmentRoleStatusResponseDto> recruitmentStatus,
        List<GetRoleDto> roles
) {
}
