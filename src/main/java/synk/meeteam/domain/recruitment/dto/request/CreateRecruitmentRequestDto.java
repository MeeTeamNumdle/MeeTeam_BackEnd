package synk.meeteam.domain.recruitment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import synk.meeteam.domain.meeteam.entity.MeeteamCategory;
import synk.meeteam.domain.meeteam.entity.MeeteamProceed;
import synk.meeteam.domain.meeteam.entity.MeeteamScope;
import synk.meeteam.domain.recruitment.dto.CreateRecruitmentRoleAndSpecDto;

@Schema(name = "CreateRecruitmentRequestDto", description = "새 구인글 생성 요청 Dto")
public record CreateRecruitmentRequestDto(

        @NotBlank
        @Schema(description = "제목", example = "졸작 사람 구합니다!!")
        String title, // 제목,
        @NotNull
        @Schema(description = "범위", example = "OFF_CAMPUS  or ON_CAMPUS")
        MeeteamScope meeteamScope, // 범위,
        // @NotBlank String field,  // 분야,
        @NotNull
        @Schema(description = "유형", example = "PROJECT or STUDY COMPETITION or CLUB")
        MeeteamCategory meeteamCategory,  // 유형,
        @NotNull
        @Schema(description = "진행방식", example = "ONLINE or OFFLINE or BOTH")
        MeeteamProceed meeteamProceed,  // 진행방식,
        @NotNull
        @Schema(description = "시작 기간", example = "2023-02-01")
        LocalDate proceedingStart,  // 시작 기간
        @NotNull
        @Schema(description = "종료 기간", example = "2023-07-01")
        LocalDate proceedingEnd, // 종료 기간
        @NotNull
        @Schema(description = "구인 마감일", example = "2023-02-05")
        LocalDate deadline, // 구인 마감일
        @NotNull
        @Schema(description = "공개여부", example = "true or false")
        Boolean isPublic,  // 공개여부,
        @NotNull
        @Schema(description = "수업 관련 정보", example = "")
        CreateRecruitmentCourseRequestDto courseRequestDto, //  수업,
        @NotNull
        @Schema(description = "태그", example = "[웹개발, AI, 졸업작품]")
        List<String> meeteamTags,  //  태그,
        @NotNull
        @Schema(description = "구인 내용", example = "안녕하세요. 사람구하는데 ~~")
        String content,  // 구인 내용,
        @NotNull
        @Schema(description = "필요한 역할들(List 형태로)", example = "")
        List<CreateRecruitmentRoleAndSpecDto> recruitmentRoles // 역할 공간 설정
) {
}
