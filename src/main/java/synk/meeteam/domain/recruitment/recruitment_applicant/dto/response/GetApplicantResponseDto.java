package synk.meeteam.domain.recruitment.recruitment_applicant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetApplicantResponseDto", description = "신청자 조회 Dto")
public record GetApplicantResponseDto(
        @Schema(description = "신청자 id", example = "4OaVE421DSwR63xfKf6vxA==")
        String userId,
        @Schema(description = "신청자 닉네임", example = "minji_98")
        String nickname,
        @Schema(description = "신청자 프로필 사진", example = "url형태")
        String profileImg,
        @Schema(description = "신청자 이름", example = "김민지")
        String name,
        @Schema(description = "신청자 평점", example = "4.4")
        double score,
        @Schema(description = "대학 이름", example = "광운대학교")
        String universityName,
        @Schema(description = "학과 이름", example = "소프트웨어학부")
        String departmentName,
        @Schema(description = "입학년도", example = "2018")
        int year,
        @Schema(description = "신청 역할", example = "백엔드개발자")
        String applyRoleName,
        @Schema(description = "전하는 말", example = "저 관심있어용")
        String message

) {
}
