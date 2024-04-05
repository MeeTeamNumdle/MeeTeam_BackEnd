package synk.meeteam.domain.recruitment.recruitment_applicant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(name = "ApproveApplicantRequestDto", description = "신청자 승인/거절 Dto")
public record ProcessApplicantRequestDto(
        @NotNull
        @Schema(description = "신청 id", example = "\"[1,2,3]\"")
        List<Long> applicantIds
) {
}
