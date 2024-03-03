package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


public record GetRecruitmentRoleResponseDto(
        @NotNull
        @Schema(description = "지원 가능한 역할", example = "백엔드개발자")
        String roleName
) {
}
