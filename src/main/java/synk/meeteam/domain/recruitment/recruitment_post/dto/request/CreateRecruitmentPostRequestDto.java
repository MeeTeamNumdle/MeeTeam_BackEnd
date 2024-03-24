package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record CreateRecruitmentPostRequestDto(
        @NotNull
        @Column(length = 10)
        @Schema(description = "범위", example = "교내")
        String scope,
        @NotNull
        @Schema(description = "유형", example = "프로젝트")
        String category,
        @NotNull
        @Schema(description = "구인 마감일", example = "2023-02-05")
        LocalDate deadline,
        @NotNull
        @Schema(description = "[시작 기간, 종료기간]", example = "[\"2023-02-01\"]")
        LocalDate proceedingStart,
        @NotNull
        @Schema(description = "[시작 기간, 종료기간]", example = "[\"2023-07-01\"]")
        LocalDate proceedingEnd,
        @NotNull
        @Schema(description = "분야 (개발 == 1)", example = "1")
        Long fieldId,
        @NotNull
        @Schema(description = "진행방식", example = "온라인")
        String proceedType,
        @NotNull
        @Schema(description = "수업 관련 정보")
        CourseTagDto courseTag,
        @NotNull
        @Schema(description = "태그", example = "[\"웹개발\", \"AI\", \"졸업작품\"]")
        List<String> Tags,
        @NotNull
        @Schema(description = "필요한 역할들(List 형태로)", example = "")
        List<RecruitmentRoleDto> recruitmentRoles,
        @NotBlank
        @Size(min = 5, max = 40)
        @Schema(description = "제목", example = "졸작 사람 구합니다!!")
        String title,
        @NotNull
        @Schema(description = "구인 내용", example = "안녕하세요. 사람구하는데 진짜 고수만 구해요..")
        String content
) {
}
