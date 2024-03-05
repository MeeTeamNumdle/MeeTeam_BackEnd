package synk.meeteam.domain.recruitment.recruitment_post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.recruitment.recruitment_post.api.RecruitmentPostController;
import synk.meeteam.domain.recruitment.recruitment_post.facade.RecruitmentPostFacade;

@ExtendWith(MockitoExtension.class)
public class RecruitmentPostControllerTest {

    @InjectMocks
    private RecruitmentPostController recruitmentPostController;

    @Mock
    private RecruitmentPostFacade recruitmentPostFacade;

    @Test
    void 구인글생성_생성된구인글id반환_정상입력의경우() {
        // given

        // when

        // then
    }
}
