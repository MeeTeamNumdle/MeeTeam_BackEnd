package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RecruitmentRoleDto(
        @NotNull
        @Schema(description = "필요한 역할", example = "백엔드개발자")
        Long role,
        @Schema(description = "필요한 사람 수 ", example = "2")
        int count,
        @NotNull
        @Schema(description = "필요한 스킬들", example = "[\"spring\", \"java\", \"JPA\"]")
        List<Long> skillNames
) {
}
