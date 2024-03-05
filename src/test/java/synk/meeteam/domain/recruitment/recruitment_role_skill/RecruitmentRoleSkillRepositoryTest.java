package synk.meeteam.domain.recruitment.recruitment_role_skill;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.repository.SkillRepository;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.repository.RecruitmentRoleRepository;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_role_skill.repository.RecruitmentRoleSkillRepository;

@DataJpaTest
@ActiveProfiles("test")
public class RecruitmentRoleSkillRepositoryTest {
    @Autowired
    private RecruitmentRoleSkillRepository recruitmentRoleSkillRepository;

    @Autowired
    private RecruitmentRoleRepository recruitmentRoleRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Test
    void 구인역할스킬저장_구인역할스킬저장성공_정상입력경우() {
        // given
        RecruitmentRole recruitmentRole = recruitmentRoleRepository.findById(1L).orElse(null);
        Skill skill = skillRepository.findById(6L).orElse(null);
        RecruitmentRoleSkill recruitmentRoleSkill = RecruitmentRoleSkillFixture.createRecruitmentRoleSkill(
                recruitmentRole, skill);

        // when
        RecruitmentRoleSkill savedRecruitmentRoleSkill = recruitmentRoleSkillRepository.saveAndFlush(
                recruitmentRoleSkill);
        RecruitmentRoleSkill foundRecruitmentRoleSkill = recruitmentRoleSkillRepository.findById(
                savedRecruitmentRoleSkill.getId()).orElse(null);

        // then
        Assertions.assertThat(foundRecruitmentRoleSkill)
                .extracting("id", "recruitmentRole", "skill")
                .containsExactly(savedRecruitmentRoleSkill.getId(), savedRecruitmentRoleSkill.getRecruitmentRole(),
                        savedRecruitmentRoleSkill.getSkill());
    }

    @Test
    void 구인역할스킬저장_예외발생_RecruitmentRole이null인경우() {
        // given
        RecruitmentRole recruitmentRole = null;
        Skill skill = skillRepository.findById(2L).orElse(null);
        RecruitmentRoleSkill recruitmentRoleSkill = RecruitmentRoleSkillFixture.createRecruitmentRoleSkill(
                recruitmentRole, skill);

        // when, then
        Assertions.assertThatThrownBy(() -> {
            recruitmentRoleSkillRepository.saveAndFlush(recruitmentRoleSkill);
        }).isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void 구인역할스킬저장_예외발생_skill이null인경우() {
        // given
        RecruitmentRole recruitmentRole = recruitmentRoleRepository.findById(1L).orElse(null);
        Skill skill = null;
        RecruitmentRoleSkill recruitmentRoleSkill = RecruitmentRoleSkillFixture.createRecruitmentRoleSkill(
                recruitmentRole, skill);

        // when, then
        Assertions.assertThatThrownBy(() -> {
            recruitmentRoleSkillRepository.saveAndFlush(recruitmentRoleSkill);
        }).isInstanceOf(DataIntegrityViolationException.class);

    }
}
