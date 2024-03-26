package synk.meeteam.domain.recruitment.recruitment_role_skill;

import static org.mockito.Mockito.doReturn;
import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    private List<RecruitmentRoleSkill> recruitmentRoleSkills;

    private RecruitmentRoleSkill recruitmentRoleSkill_1;

    private RecruitmentRoleSkill recruitmentRoleSkill_2;

    @BeforeEach
    void init() {
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Role role_1 = RoleFixture.createRole("백엔드개발자");
        Skill skill_1 = SkillFixture.crateSkill("spring");
        RecruitmentRole recruitmentRole_1 = RecruitmentRoleFixture.createRecruitmentRoleFixture(recruitmentPost, role_1,
                3L);
        Role role_2 = RoleFixture.createRole("프론트엔드개발자");
        Skill skill_2 = SkillFixture.crateSkill("자바스크립트");
        RecruitmentRole recruitmentRole_2 = RecruitmentRoleFixture.createRecruitmentRoleFixture(recruitmentPost, role_2,
                3L);
        recruitmentRoleSkill_1 = RecruitmentRoleSkillFixture.createRecruitmentRoleSkill(
                recruitmentRole_1, skill_1);
        recruitmentRoleSkill_2 = RecruitmentRoleSkillFixture.createRecruitmentRoleSkill(
                recruitmentRole_2, skill_2);
        recruitmentRoleSkills = new ArrayList<>();
        recruitmentRoleSkills.add(recruitmentRoleSkill_1);
        recruitmentRoleSkills.add(recruitmentRoleSkill_2);
    }

    @Test
    void 구인역할스킬저장_구인역할스킬저장성공_정상입력경우() {
        // given
        doReturn(recruitmentRoleSkills).when(recruitmentRoleSkillRepository).saveAll(recruitmentRoleSkills);

        // when
        List<RecruitmentRoleSkill> savedRecruitmentRoleSkills = recruitmentRoleSkillService.createRecruitmentRoleSkills(
                recruitmentRoleSkills);

        // then
        Assertions.assertThat(savedRecruitmentRoleSkills.get(0))
                .extracting("recruitmentRole", "skill")
                .containsExactly(recruitmentRoleSkill_1.getRecruitmentRole(), recruitmentRoleSkill_1.getSkill());
    }
}
