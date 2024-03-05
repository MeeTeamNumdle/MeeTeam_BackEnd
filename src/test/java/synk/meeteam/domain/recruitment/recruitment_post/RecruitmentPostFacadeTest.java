package synk.meeteam.domain.recruitment.recruitment_post;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.recruitment.recruitment_post.facade.RecruitmentPostFacade;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;
import synk.meeteam.domain.recruitment.recruitment_role_skill.service.RecruitmentRoleSkillService;
import synk.meeteam.domain.recruitment.recruitment_tag.service.RecruitmentTagService;

@ExtendWith(MockitoExtension.class)
public class RecruitmentPostFacadeTest {
    @InjectMocks
    private RecruitmentPostFacade recruitmentPostFacade;

    @Mock
    private RecruitmentPostService recruitmentPostService;
    @Mock
    private RecruitmentRoleService recruitmentRoleService;
    @Mock
    private RecruitmentRoleSkillService recruitmentRoleSkillService;
    @Mock
    private RecruitmentTagService recruitmentTagService;

    @Test
    void 아귀찮다() {
        // given

        // when

        // then
    }
}
