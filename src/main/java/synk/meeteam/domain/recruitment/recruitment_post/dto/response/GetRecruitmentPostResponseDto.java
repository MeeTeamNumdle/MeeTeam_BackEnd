package synk.meeteam.domain.recruitment.recruitment_post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "GetRecruitmentPostResponseDto", description = "구인글 조회 Dto")
public record GetRecruitmentPostResponseDto(
        @Schema(description = "내가 해당 글의 작성자인지", example = "true")
        boolean isWriter,
        @Schema(description = "글 제목", example = "팀원을 구합니다!")
        String title,
        @Schema(description = "해당 글 작성날짜", example = "2024-03-15")
        String createdAt,
        @Schema(description = "북마크 횟수", example = "13")
        int bookmarkCount,
        @Schema(description = "작성자 닉네임", example = "song123")
        String writerNickname,
        @Schema(description = "응답률", example = "90")
        int responseRate,
        @Schema(description = "작성자 평점", example = "4.5")
        double writerScore,
        @Schema(description = "진행 시작 날짜", example = "2024-04-15")
        String proceedingStart,
        @Schema(description = "진행 종료 날짜", example = "2024-07-15")
        String proceedingEnd,
        @Schema(description = "진행타입", example = "온/오프라인")
        String proceedType,
        @Schema(description = "구인 마감 날짜", example = "2024-03-10")
        String deadline,
        @Schema(description = "범위", example = "교내")
        String scope,
        @Schema(description = "수업 이름", example = "공학설계입문")
        String courseName,
        @Schema(description = "교수 이름", example = "최웅철")
        String courseProfessor,
        @Schema(description = "구인 태그", example = "")
        List<TagDto> Tags,
        @Schema(description = "구인 역할", example = "")
        List<GetRecruitmentRoleResponseDto> recruitmentRoles,
        @Schema(description = "상세 내용", example = "안녕하세요. 저는 팀원을...")
        String content,
        @Schema(description = "댓글, 대댓글", example = "")
        List<GetCommentResponseDto> comments

) {
}
