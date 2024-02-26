package synk.meeteam.domain.common.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record GetDepartmentRequestDto(
        @NotNull
        @Schema(description = "학교 id", example = "1")
        Long universityId
) {
}
