package synk.meeteam.domain.recruitment.recruitment_role_skill;

import static org.mockito.Mockito.doReturn;
import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.role.RoleFixture;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.skill.SkillFixture;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_role.RecruitmentRoleFixture;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_role_skill.repository.RecruitmentRoleSkillRepository;
import synk.meeteam.domain.recruitment.recruitment_role_skill.service.RecruitmentRoleSkillService;

@ExtendWith(MockitoExtension.class)
public class RecruitmentRoleSkillServiceTest {
    @InjectMocks
    private RecruitmentRoleSkillService recruitmentRoleSkillService;

    @Mock
    private RecruitmentRoleSkillRepository recruitmentRoleSkillRepository;

    @Test
    void 구인역할스킬저장_구인역할스킬저장성공_정상입력경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Role role = RoleFixture.createRole("백엔드개발자");
        RecruitmentRole recruitmentRole = RecruitmentRoleFixture.createRecruitmentRoleFixture(recruitmentPost, role,
                3L);
        Skill skill = SkillFixture.crateSkill("spring");
        RecruitmentRoleSkill recruitmentRoleSkill = RecruitmentRoleSkillFixture.createRecruitmentRoleSkill(
                recruitmentRole, skill);
        doReturn(recruitmentRoleSkill).when(recruitmentRoleSkillRepository).save(recruitmentRoleSkill);

        // when
        RecruitmentRoleSkill savedRecruitmentRoleSkill = recruitmentRoleSkillService.createRecruitmentRoleSkill(
                recruitmentRoleSkill);

        // then
        Assertions.assertThat(savedRecruitmentRoleSkill)
                .extracting("recruitmentRole", "skill")
                .containsExactly(recruitmentRoleSkill.getRecruitmentRole(), recruitmentRoleSkill.getSkill());
    }
}
