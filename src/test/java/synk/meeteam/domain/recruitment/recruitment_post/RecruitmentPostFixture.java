package synk.meeteam.domain.recruitment.recruitment_post;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.PaginationSearchPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.SimpleRecruitmentPostDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.repository.vo.RecruitmentPostVo;
import synk.meeteam.global.dto.PageInfo;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;

public class RecruitmentPostFixture {

    public static final String TITLE = "정상적인 제목입니다.";
    public static final String TITLE_EXCEED_40 = "구인글 생성을 할 때 이 제목은 예외가 발생해야 하는 제목입니다. 왜냐하면 40자가 넘기 떄문입니다. 왜냐하면 40자가 넘기 떄문입니다. 왜냐하면 40자가 넘기 떄문입니다.";
    public static Field field = new Field(1L, "개발");

    public static RecruitmentPost createRecruitmentPost(String title) {
        return RecruitmentPost.builder()
                .title(title)
                .content("정상적인 내용")
                .scope(Scope.ON_CAMPUS)
                .category(Category.PROJECT)
                .field(field)
                .proceedType(ProceedType.ON_LINE)
                .proceedingStart(LocalDate.of(2024, 2, 28))
                .proceedingEnd(LocalDate.of(2024, 7, 28))
                .deadline(LocalDate.of(2024, 3, 2))
                .bookmarkCount(5L)
                .build();
    }

    public static RecruitmentPost createRecruitmentPost_bookmark(Long bookmarkCount) {
        return RecruitmentPost.builder()
                .title("정상 제목입니다.")
                .content("정상적인 내용")
                .scope(Scope.ON_CAMPUS)
                .category(Category.PROJECT)
                .field(field)
                .proceedType(ProceedType.ON_LINE)
                .proceedingStart(LocalDate.of(2024, 2, 28))
                .proceedingEnd(LocalDate.of(2024, 7, 28))
                .deadline(LocalDate.of(2024, 3, 2))
                .bookmarkCount(bookmarkCount)
                .build();
    }

    public static PaginationSearchPostResponseDto createPageSearchPostResponseDto() {
        List<SimpleRecruitmentPostDto> simpleRecruitmentPostDtos = List.of(
                new SimpleRecruitmentPostDto(1L, "제목", "프로젝트", "작성자", "이미지", "2022-03-03", "교외", true),
                new SimpleRecruitmentPostDto(2L, "제목2", "스터디", "작성자", "이미지", "2022-03-03", "교내", false)
        );
        return new PaginationSearchPostResponseDto(simpleRecruitmentPostDtos, new PageInfo(1, 24, 3L, 1));
    }

    public static Page<RecruitmentPostVo> createPagePostVo() {
        return new PageImpl<>(List.of(
                createTitlePostVo("제목1"),
                createTitlePostVo("제목2"),
                createTitlePostVo("제목3")
        ));
    }

    public static RecruitmentPostVo createTitlePostVo(String title) {
        return RecruitmentPostVo.builder()
                .title(title)
                .writerNickname("작성자")
                .scope(Scope.ON_CAMPUS)
                .category(Category.STUDY)
                .isBookmarked(false)
                .build();
    }

}
