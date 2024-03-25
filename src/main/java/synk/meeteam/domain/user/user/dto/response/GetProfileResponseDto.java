package synk.meeteam.domain.user.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;

public record GetProfileResponseDto(
        @Schema(description = "프로필 이미지 url", example = "https://image.png")
        String imageUrl,
        @Schema(description = "이름", example = "민지")
        String userName,
        @Schema(description = "닉네임", example = "minji")
        String nickname,
        @Schema(description = "이름 공개여부", example = "false")
        boolean isUserNamePublic,
        @Schema(description = "관심있는역할", example = "백엔드 개발자")
        String interest,
        @Schema(description = "한줄 소개", example = "한줄소개입니다.")
        String introduction,
        @Schema(description = "자기소개", example = "한줄소개보다 구체적인 자기소개입니다.")
        String aboutMe,
        GetProfileEmailDto universityEmail,
        GetProfileEmailDto subEmail,
        GetProfilePhoneDto phone,
        @Schema(description = "대학교", example = "광운대학교")
        String university,
        @Schema(description = "학과", example = "소프트웨어학부")
        String department,
        @Schema(description = "최대학점", example = "4.5")
        Double maxGpa,
        @Schema(description = "학점", example = "4.3")
        Double gpa,
        @Schema(description = "입학년도", example = "2019")
        int year,
        List<GetProfilePortfolioDto> portfolios,
        List<GetProfileLinkDto> links,
        List<GetProfileAwardDto> awards,
        List<SkillDto> skills

) {
}
