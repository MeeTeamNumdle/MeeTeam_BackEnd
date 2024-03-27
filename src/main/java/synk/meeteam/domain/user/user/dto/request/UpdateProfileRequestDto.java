package synk.meeteam.domain.user.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import synk.meeteam.domain.user.award.dto.UpdateAwardDto;
import synk.meeteam.domain.user.user_link.dto.UpdateUserLinkDto;

@Schema(name = "UpdateProfileResponseDto", description = "유저 정보 저장 요청 Dto")
public record UpdateProfileRequestDto(

        @Schema(description = "이름", example = "부겸")
        @NotBlank
        String name,

        @Schema(description = "닉네임", example = "goder")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣._]*$", message = "닉네임은 영어, 숫자, 한글, '_', '.' 문자만 가능합니다.")
        @NotBlank
        String nickname,

        @Schema(description = "프로필사진 url")
        @NotBlank
        String imageFileName,

        @Schema(description = "전화정보", example = "010-1234-5678")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 약식과 맞지 않습니다. xxx-xxxx-xxxx")
        String phoneNumber,

        @Schema(description = "전화번호 공개여부")
        boolean isPublicPhone,

        @Schema(description = "학교 이메일이 주 이메일인지", example = "true")
        boolean isSchoolMain,

        @Schema(description = "보조 이메일")
        @Email
        String subEmail,

        @Schema(description = "보조이메일 공개 여부")
        boolean isPublicSubEmail,

        @Schema(description = "학교이메일 공개여부")
        boolean isPublicUniversityEmail,

        @Schema(description = "한줄소개", example = "고통을 즐기는 개발자")
        String introduction,

        @Schema(description = "자기소개", example = "안녕하세요. 저는 프로그래밍 자체를 좋아하지만, 아직은 부족한 백엔드 개발자 지망생입니다. 협업의 중요하게 생각하며 다양한 방식의 커뮤니케이션을 하기 위해 노력하고 있으며, 더 나은 코드를 만들기 위한 지식을 쌓는 중에 있습니다.")
        String aboutMe,

        @Schema(description = "관심있는 역할명", example = "서버 개발자")
        Long interest_id,

        @Schema(description = "학점", example = "4.0")
        Double gpa,

        @Schema(description = "최대학점", example = "4.5")
        Double maxGpa,

        @Schema(description = "스킬 id 정보", example = "[1,2,3]")
        List<Long> skills,

        @Schema(description = "링크 목록")
        List<UpdateUserLinkDto> links,

        @Schema(description = "수상내역")
        List<UpdateAwardDto> awards,

        @Schema(description = "포트폴리오 순서", example = "[1,2,3]")
        List<Long> portfolios

) {

}
