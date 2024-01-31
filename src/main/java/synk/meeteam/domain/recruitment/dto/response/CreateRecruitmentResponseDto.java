package synk.meeteam.domain.recruitment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateRecruitmentResponseDto", description = "새 구인글 생성에 대한 응답값")
public record CreateRecruitmentResponseDto(
        @Schema(description = "플랫폼 Id", example = "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM")
        String platformId
) {
    public static CreateRecruitmentResponseDto of(final String platformId){
        return new CreateRecruitmentResponseDto(platformId);
    }
}
