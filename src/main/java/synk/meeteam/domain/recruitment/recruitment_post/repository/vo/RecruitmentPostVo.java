package synk.meeteam.domain.recruitment.recruitment_post.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.Scope;

@Data
public class RecruitmentPostVo {
    private Long id;
    private String title;
    private Category category;
    private Scope scope;
    private String writerNickname;
    private String writerProfileImg;
    private LocalDate deadline;
    private Boolean isBookmarked;
    private LocalDateTime createdAt;

    @Builder
    @QueryProjection
    public RecruitmentPostVo(Long id, String title, Category category, String writerNickname, String writerProfileImg,
                             LocalDate deadline, Scope scope, Boolean isBookmarked, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.writerNickname = writerNickname;
        this.writerProfileImg = writerProfileImg;
        this.deadline = deadline;
        this.scope = scope;
        this.isBookmarked = isBookmarked;
        this.createdAt = createdAt;
    }
}
