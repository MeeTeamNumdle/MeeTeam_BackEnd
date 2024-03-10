package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import synk.meeteam.domain.common.role.dto.RoleDto;

@Schema(name = "GetRecruitmentPostApplyInfoResponseDto", description = "신청 정보 조회 Dto")
public record GetApplyInfoResponseDto(
        @Schema(description = "신청자 이름", example = "송민규")
        String name,
        @Schema(description = "신청자 평점", example = "4.4")
        double score,
        @Schema(description = "대학 이름", example = "광운대학교")
        String universityName,
        @Schema(description = "학과 이름", example = "소프트웨어학부")
        String departmentName,
        @Schema(description = "입학년도", example = "2018")
        int year,
        @Schema(description = "학사 이메일", example = "thdalsrb123@kw.ac.kr")
        String email,
        @Schema(description = "지원 가능한 구인 역할", example = "")
        List<RoleDto> recruitmentRoles

) {
}
