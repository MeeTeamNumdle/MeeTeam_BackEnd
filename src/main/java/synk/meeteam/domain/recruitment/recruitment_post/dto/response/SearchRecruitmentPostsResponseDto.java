package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class SearchRecruitmentPostsResponseDto {
    @Schema(description = "글 id", example = "1")
    private Long id;
    @Schema(description = "글 제목", example = "팀원을 구합니다!")
    private String title;
    @Schema(description = "유형", example = "프로젝트")
    private String category;
    @Schema(description = "작성자 닉네임", example = "song123")
    private String writerNickname;
    @Schema(description = "작성자 사진", example = "url 형태")
    private String writerProfileImg;
    @Schema(description = "구인 마감 날짜", example = "2024-03-10")
    private String deadline;
    @Schema(description = "범위", example = "교내")
    private String scope;
    @Schema(description = "북마크 여부", example = "true")
    private Boolean isBookmarked;

    @Builder
    @QueryProjection
    public SearchRecruitmentPostsResponseDto(Long id, String title, String category, String writerNickname,
                                             String writerProfileImg, String deadline, String scope,
                                             Boolean isBookmarked) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.writerNickname = writerNickname;
        this.writerProfileImg = writerProfileImg;
        this.deadline = deadline;
        this.scope = scope;
        this.isBookmarked = isBookmarked;
    }
}
