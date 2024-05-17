package synk.meeteam.domain.user.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetProfileImageResponseDto(
        @Schema(description = "프로필 이미지 url", example = "https://image.png")
        String imageUrl
) {
        public static GetProfileImageResponseDto of(String imageUrl){
                return new GetProfileImageResponseDto(imageUrl);
        }
}
