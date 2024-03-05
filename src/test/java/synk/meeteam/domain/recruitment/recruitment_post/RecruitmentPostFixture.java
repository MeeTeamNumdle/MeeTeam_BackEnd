package synk.meeteam.domain.recruitment.recruitment_post;

import java.time.LocalDate;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
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
                .build();
    }
}
