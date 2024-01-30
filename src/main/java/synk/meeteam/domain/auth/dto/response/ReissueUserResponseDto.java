package synk.meeteam.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "ReissueUserResponseDto", description = "토큰 재발급 요청 Dto")
public record ReissueUserResponseDto(
        @NotNull
        @Schema(description = "플랫폼 Id", example = "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM")
        String platformId,
        @NotNull
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInBsYXRmb3JtSWQiOiJEaTdsQ2hNR3hqWlZUYWk2ZDc2SG8xWUxEVV94TDh0bDFDZmRQTVY1U1FNIiwicGxhdGZvcm1UeXBlIjoiTkFWRVIiLCJpYXQiOjE3MDYyODA1MjMsImV4cCI6MTgxNDI4MDUyM30.doPtAdLQMZ8NeuhRAOg7GNMBBtFZzPOOZp60HskGtZ0")
        String accessToken,
        @NotNull
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSZWZyZXNoVG9rZW4iLCJpYXQiOjE3MDYyODA1MjMsImV4cCI6MTcwNjg4NTMyM30.yvftTGVld0ZMnv1a79wpuzuTo8EJ1zOHoSlT_jfH3cs")
        String refreshToken) {
    public static ReissueUserResponseDto of(String platformId, String accessToken,
                                            String refreshToken) {
        return new ReissueUserResponseDto(platformId, accessToken, refreshToken);
    }
}
