package synk.meeteam.domain.recruitment.recruitment_applicant;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.repository.RoleRepository;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitStatus;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_applicant.repository.RecruitmentApplicantRepository;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
@Sql({"classpath:test-recruitment-applicant.sql"})
public class RecruitmentApplicantRepositoryTest {

    @Autowired
    private RecruitmentApplicantRepository recruitmentApplicantRepository;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 신청자저장_신청자정보반환_정상경우() {
        // given
        Role role = roleRepository.findByIdOrElseThrowException(2L);
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findByIdOrElseThrow(2L);
        User user = userRepository.findByIdOrElseThrow(2L);

        RecruitmentApplicant recruitmentApplicant = RecruitmentApplicantFixture.createRecruitmentApplicant(
                recruitmentPost, user, role);

        // when
        RecruitmentApplicant savedRecruitmentApplicant = recruitmentApplicantRepository.saveAndFlush(
                recruitmentApplicant);
        RecruitmentApplicant foundRecruitmentApplicant = recruitmentApplicantRepository.findById(
                savedRecruitmentApplicant.getId()).orElse(null);

        // then
        Assertions.assertThat(foundRecruitmentApplicant)
                .extracting("recruitmentPost", "applicant", "role", "comment")
                .containsExactly(recruitmentPost, user, role, recruitmentApplicant.getComment());
    }

    @Test
    void 신청자저장_예외발생_이미신청한경우() {
        // given
        Long postId = 2L;
        Role role = roleRepository.findByIdOrElseThrowException(1L);
        Role newRole = roleRepository.findByIdOrElseThrowException(2L);

        RecruitmentPost recruitmentPost = recruitmentPostRepository.findByIdOrElseThrow(postId);
        User user = userRepository.findByIdOrElseThrow(1L);

        RecruitmentApplicant recruitmentApplicant = RecruitmentApplicantFixture.createRecruitmentApplicant(
                recruitmentPost, user, role);
        recruitmentApplicantRepository.saveAndFlush(recruitmentApplicant);

        // when, then
        RecruitmentApplicant newRecruitmentApplicant = RecruitmentApplicantFixture.createRecruitmentApplicant(
                recruitmentPost, user, newRole);
        Assertions.assertThatThrownBy(() -> {
            recruitmentApplicantRepository.saveAndFlush(newRecruitmentApplicant);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void 신청자저장_예외발생_Role이null인경우() {
        // given
        Role role = null;
        Role newRole = roleRepository.findByIdOrElseThrowException(2L);

        RecruitmentPost recruitmentPost = recruitmentPostRepository.findByIdOrElseThrow(1L);
        User user = userRepository.findByIdOrElseThrow(1L);

        RecruitmentApplicant recruitmentApplicant = RecruitmentApplicantFixture.createRecruitmentApplicant(
                recruitmentPost, user, role);
        // when, then
        Assertions.assertThatThrownBy(() -> {
            recruitmentApplicantRepository.saveAndFlush(recruitmentApplicant);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void 신청자목록조회_성공() {
        // given

        // when
        List<RecruitmentApplicant> recruitmentApplicants = recruitmentApplicantRepository.findAllInApplicantId(
                List.of(1L, 2L));

        // then
        Assertions.assertThat(recruitmentApplicants.size()).isEqualTo(2);
    }

    @Test
    void 신청자승인처리_성공() {
        // given

        // when
        recruitmentApplicantRepository.bulkApprove(List.of(1L, 2L));

        // then
        List<RecruitmentApplicant> recruitmentApplicants = recruitmentApplicantRepository.findAllInApplicantId(
                List.of(1L, 2L));
        recruitmentApplicants.stream()
                .forEach(applicant -> {
                    Assertions.assertThat(applicant.getRecruitStatus()).isEqualTo(RecruitStatus.APPROVED);
                });

    }
}
