package synk.meeteam.domain.recruitment.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import synk.meeteam.domain.field.entity.Field;
import synk.meeteam.domain.meeteam.entity.MeeteamCategory;
import synk.meeteam.domain.meeteam.entity.MeeteamProceed;
import synk.meeteam.domain.meeteam.entity.MeeteamScope;
import synk.meeteam.domain.recruitment.dto.CreateRecruitmentRoleAndSpecDto;

public record CreateRecruitmentRequestDto(

        @NotBlank String title, // 제목,
        @NotBlank MeeteamScope meeteamScope, // 범위,
        @NotBlank Field field,  // 분야,
        @NotBlank MeeteamCategory meeteamCategory,  // 유형,
        @NotBlank MeeteamProceed meeteamProceed,  // 진행방식,
        @NotBlank LocalDateTime proceedingStart,  // 시작 기간
        @NotBlank LocalDateTime proceedingEnd, // 종료 기간
        @NotBlank LocalDateTime deadline, // 구인 마감일
        @NotBlank Boolean isPublic,  // 공개여부,
        @NotBlank CreateRecruitmentCourseRequestDto courseRequestDto, //  수업,
        @NotBlank List<String> meeteamTags,  //  태그,
        @NotBlank String content,  // 구인 내용,
        String coverImageUrl, // 커버 이미지,
        @NotBlank List<CreateRecruitmentRoleAndSpecDto> recruitmentRoles // 역할 공간 설정
) {
}
