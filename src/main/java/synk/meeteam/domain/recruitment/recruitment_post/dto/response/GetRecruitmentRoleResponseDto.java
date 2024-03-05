package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;

@Schema(name = "GetRecruitmentRoleResponseDto", description = "구인역할 조회 Dto")
public record GetRecruitmentRoleResponseDto(
        @Schema(description = "구인 역할 이름", example = "백엔드개발자")
        String roleName,
        @Schema(description = "구인 역할 스킬", example = "")
        List<SkillDto> skills,
        @Schema(description = "구인하는 인원", example = "5")
        long recruitCount,
        @Schema(description = "지원 인원", example = "10")
        long applicantCount,
        @Schema(description = "구인된 인원", example = "2")
        long recruitedCount

) {
    @Builder
    public GetRecruitmentRoleResponseDto(String roleName, List<SkillDto> skills, long recruitCount, long applicantCount,
                                         long recruitedCount) {
        this.roleName = roleName;
        this.skills = skills;
        this.recruitCount = recruitCount;
        this.applicantCount = applicantCount;
        this.recruitedCount = recruitedCount;
    }

    public static GetRecruitmentRoleResponseDto from(RecruitmentRole recruitmentRole) {
        List<SkillDto> skillDtos = recruitmentRole.getRecruitmentRoleSkills().stream().map(SkillDto::from).toList();

        return GetRecruitmentRoleResponseDto.builder()
                .roleName(recruitmentRole.getRole().getName())
                .skills(skillDtos)
                .recruitCount(recruitmentRole.getCount())
                .applicantCount(recruitmentRole.getApplicantCount())
                .recruitedCount(recruitmentRole.getRecruitedCount())
                .build();
    }
}
