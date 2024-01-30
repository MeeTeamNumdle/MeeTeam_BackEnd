package synk.meeteam.domain.recruitment.dto.response;

public record CreateRecruitmentResponseDto(
    String platformId
) {
    public static CreateRecruitmentResponseDto of(final String platformId){
        return new CreateRecruitmentResponseDto(platformId);
    }
}
