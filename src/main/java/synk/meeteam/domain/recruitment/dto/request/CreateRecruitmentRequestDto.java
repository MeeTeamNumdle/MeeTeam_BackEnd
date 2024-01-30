package synk.meeteam.domain.recruitment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import synk.meeteam.domain.meeteam.entity.MeeteamCategory;
import synk.meeteam.domain.meeteam.entity.MeeteamProceed;
import synk.meeteam.domain.meeteam.entity.MeeteamScope;
import synk.meeteam.domain.recruitment.dto.CreateRecruitmentRoleAndSpecDto;

public record CreateRecruitmentRequestDto(

        @NotBlank String title, // 제목,
        @NotNull MeeteamScope meeteamScope, // 범위,
        // @NotBlank String field,  // 분야,
        @NotNull MeeteamCategory meeteamCategory,  // 유형,
        @NotNull MeeteamProceed meeteamProceed,  // 진행방식,
        @NotNull LocalDate proceedingStart,  // 시작 기간
        @NotNull LocalDate proceedingEnd, // 종료 기간
        @NotNull LocalDate deadline, // 구인 마감일
        @NotNull Boolean isPublic,  // 공개여부,
        @NotNull CreateRecruitmentCourseRequestDto courseRequestDto, //  수업,
        @NotNull List<String> meeteamTags,  //  태그,
        @NotNull String content,  // 구인 내용,
        String coverImageUrl, // 커버 이미지,
        @NotNull List<CreateRecruitmentRoleAndSpecDto> recruitmentRoles // 역할 공간 설정
) {
}
