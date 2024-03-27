package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import java.util.List;
import synk.meeteam.global.dto.PageInfo;

public record PaginationSearchPostResponseDto(
        List<SearchRecruitmentPostsResponseDto> posts,
        PageInfo pageInfo
) {
}
