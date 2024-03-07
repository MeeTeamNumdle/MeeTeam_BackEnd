package synk.meeteam.domain.user.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import synk.meeteam.domain.user.award.dto.CreateAwardDto;
import synk.meeteam.domain.user.sub_email.dto.CreateSubEmailDto;
import synk.meeteam.domain.user.user_link.dto.SaveUserLinkDto;

@Schema(name = "SaveProfileResponseDto", description = "유저 정보 저장 요청 Dto")
public record UpdateProfileRequestDto(

        @Schema(description = "닉네임", example = "goder")
        String nickname,

        @Schema(description = "프로필사진 url")
        String imageUrl,
        @Schema(description = "이메일 공개 여부", example = "true")
        boolean isEmailPublic,

        @Schema(description = "전화정보")
        UpdatePhoneNumber phone,

        @Schema(description = "한줄소개", example = "고통을 즐기는 개발자")
        String introduction,

        @Schema(description = "자기소개", example = "안녕하세요. 저는 프로그래밍 자체를 좋아하지만, 아직은 부족한 백엔드 개발자 지망생입니다. 협업의 중요하게 생각하며 다양한 방식의 커뮤니케이션을 하기 위해 노력하고 있으며, 더 나은 코드를 만들기 위한 지식을 쌓는 중에 있습니다.")
        String aboutMe,

        @Schema(description = "관심있는 역할명", example = "서버 개발자")
        String interest,

        @Schema(description = "학점", example = "4.0")
        Double gpa,

        @Schema(description = "최대학점", example = "4.5")
        Double maxGpa,

        @Schema(description = "스킬 id 정보", example = "[1,2,3]")
        List<Long> skills,

        @Schema(description = "링크 목록")
        List<SaveUserLinkDto> links,

        @Schema(description = "서브 이메일")
        List<CreateSubEmailDto> subEmails,

        @Schema(description = "수상내역")
        List<CreateAwardDto> awards,

        @Schema(description = "포트폴리오 순서", example = "[1,2,3]")
        List<Long> portfolios

) {

}
