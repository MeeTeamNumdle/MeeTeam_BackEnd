package synk.meeteam.domain.recruitment.recruitment_post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;

public record CreateRecruitmentPostRequestDto(
        @NotNull
        @Schema(description = "범위", example = "교내")
        Scope scope,
        @NotNull
        @Schema(description = "유형", example = "프로젝트")
        Category category,
        @NotNull
        @Schema(description = "구인 마감일", example = "2023-02-05")
        LocalDate deadline,
        @NotNull
        @Schema(description = "시작 기간", example = "2023-02-01")
        LocalDate proceedingStart,
        @NotNull
        @Schema(description = "종료 기간", example = "2023-07-01")
        LocalDate proceedingEnd,
        @NotBlank
        @Schema(description = "분야", example = "개발")
        String field,
        @NotNull
        @Schema(description = "진행방식", example = "온라인")
        ProceedType proceedType,
        @NotNull
        @Schema(description = "수업 관련 정보")
        CourseTagDto courseTag,
        @NotNull
        @Schema(description = "태그", example = "[웹개발, AI, 졸업작품]")
        List<String> Tags,
        @NotNull
        @Schema(description = "필요한 역할들(List 형태로)", example = "")
        List<RecruitmentRoleDto> recruitmentRoles,
        @NotBlank
        @Schema(description = "제목", example = "졸작 사람 구합니다!!")
        String title,
        @NotNull
        @Schema(description = "구인 내용", example = "안녕하세요. 사람구하는데 진짜 고수만 구해요..")
        String content

) {
}
