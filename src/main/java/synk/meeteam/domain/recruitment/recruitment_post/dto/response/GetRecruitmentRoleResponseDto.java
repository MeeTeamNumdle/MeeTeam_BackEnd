package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import synk.meeteam.domain.common.skill.dto.SkillDto;

@Schema(name = "GetRecruitmentRoleResponseDto", description = "구인역할 조회 Dto")
public record GetRecruitmentRoleResponseDto(
        @Schema(description = "구인 역할 이름", example = "백엔드개발자")
        String roleName,
        @Schema(description = "구인 역할 스킬", example = "")
        List<SkillDto> skills,
        @Schema(description = "구인하는 인원", example = "5")
        int recruitCount,
        @Schema(description = "지원 인원", example = "10")
        int applicantCount,
        @Schema(description = "구인된 인원", example = "2")
        int recruitedCount

) {
}
