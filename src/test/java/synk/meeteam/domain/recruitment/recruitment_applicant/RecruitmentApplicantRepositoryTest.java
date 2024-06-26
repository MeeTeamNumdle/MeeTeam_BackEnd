package synk.meeteam.domain.recruitment.recruitment_applicant;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.repository.RoleRepository;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantDto;
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
        recruitmentApplicantRepository.updateRecruitStatus(List.of(1L, 2L), RecruitStatus.APPROVED);

        // then
        List<RecruitmentApplicant> recruitmentApplicants = recruitmentApplicantRepository.findAllInApplicantId(
                List.of(1L, 2L));
        recruitmentApplicants.stream()
                .forEach(applicant -> {
                    Assertions.assertThat(applicant.getRecruitStatus()).isEqualTo(RecruitStatus.APPROVED);
                });

    }

    @Test
    void 신청자거절처리_성공() {
        // given

        // when
        recruitmentApplicantRepository.updateRecruitStatus(List.of(1L, 2L), RecruitStatus.REJECTED);

        // then
        List<RecruitmentApplicant> recruitmentApplicants = recruitmentApplicantRepository.findAllInApplicantId(
                List.of(1L, 2L));
        recruitmentApplicants.stream()
                .forEach(applicant -> {
                    Assertions.assertThat(applicant.getRecruitStatus()).isEqualTo(RecruitStatus.REJECTED);
                });
    }

    @Test
    void 신청자목록조회_NONE신청자목록조회_role설정하지않은경우() {
        // given
        Long postId = 1L;
        int page = 1;
        Pageable pageable = PageRequest.of(page - 1, 8);
        // when

        Slice<GetApplicantDto> responseDtos = recruitmentApplicantRepository.findByPostIdAndRoleId(postId, null,
                pageable);

        // then
        Assertions.assertThat(responseDtos.getContent().size()).isEqualTo(2);
        Assertions.assertThat(responseDtos.getContent().get(0))
                .extracting("nickname", "name", "applyRoleName", "message")
                .containsExactly("송민규짱짱맨", "송민규", "소프트웨어 엔지니어", "나 잘할 수 있음");
        Assertions.assertThat(responseDtos.getContent().get(1))
                .extracting("nickname", "name", "applyRoleName", "message")
                .containsExactly("나부겸짱짱맨", "나부겸", "웹 개발자", "나 잘할 수 있여");
    }

    @Test
    void 신청자목록조회_NONE이면서해당role신청자목록조회_role설정한경우() {
        // given
        Long postId = 1L;
        Long roleId = 2L;
        int page = 1;
        Pageable pageable = PageRequest.of(page - 1, 8);

        // when
        Slice<GetApplicantDto> responseDtos = recruitmentApplicantRepository.findByPostIdAndRoleId(postId,
                roleId, pageable);

        // then
        Assertions.assertThat(responseDtos.getContent().size()).isEqualTo(1);
        Assertions.assertThat(responseDtos.getContent().get(0))
                .extracting("nickname", "name", "applyRoleName", "message")
                .containsExactly("나부겸짱짱맨", "나부겸", "웹 개발자", "나 잘할 수 있여");
    }
}
