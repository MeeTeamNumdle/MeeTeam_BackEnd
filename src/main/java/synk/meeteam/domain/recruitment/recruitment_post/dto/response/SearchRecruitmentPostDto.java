package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SearchRecruitmentPostDto(
        @Schema(description = "글 id", example = "1")
        Long id,
        @Schema(description = "글 제목", example = "팀원을 구합니다!")
        String title,
        @Schema(description = "유형", example = "프로젝트")
        String category,
        @Schema(description = "작성자 닉네임", example = "song123")
        String writerNickname,
        @Schema(description = "작성자 사진", example = "url 형태")
        String writerProfileImg,
        @Schema(description = "구인 마감 날짜", example = "2024-03-10")
        String deadline,
        @Schema(description = "범위", example = "교내")
        String scope,
        @Schema(description = "북마크 여부", example = "true")
        Boolean isBookmarked
) {
}
