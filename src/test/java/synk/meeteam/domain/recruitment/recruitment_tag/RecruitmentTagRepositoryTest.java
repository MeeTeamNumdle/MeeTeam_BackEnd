package synk.meeteam.domain.recruitment.recruitment_tag;

import static org.assertj.core.api.Assertions.assertThat;
import static synk.meeteam.domain.common.tag.TagFixture.NAME_EXCEED_15;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.tag.TagFixture;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.repository.TagRepository;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.repository.RecruitmentPostRepository;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;
import synk.meeteam.domain.recruitment.recruitment_tag.repository.RecruitmentTagRepository;

@DataJpaTest
@ActiveProfiles("test")
public class RecruitmentTagRepositoryTest {
    @Autowired
    private RecruitmentTagRepository recruitmentTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    @Test
    void 구인태그저장_구인태그반환_정상입력경우() {
        // given
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(1L).orElse(null);
        Tag tag = tagRepository.findById(1L).orElse(null);
        RecruitmentTag recruitmentTag = RecruitmentTagFixture.createRecruitmentTag(recruitmentPost, tag);

        // when
        RecruitmentTag savedRecruitmentTag = recruitmentTagRepository.saveAndFlush(recruitmentTag);
        RecruitmentTag foundRecruitmentTag = recruitmentTagRepository.findById(savedRecruitmentTag.getId())
                .orElse(null);

        // then
        assertThat(foundRecruitmentTag)
                .extracting("recruitmentPost", "tag")
                .containsExactly(savedRecruitmentTag.getRecruitmentPost(), savedRecruitmentTag.getTag());
    }

    @Test
    void 구인태그저장_예외발생_recruitmentPost가null인경우() {
        // given
        RecruitmentPost recruitmentPost = null;
        Tag tag = tagRepository.findById(1L).orElse(null);
        RecruitmentTag recruitmentTag = RecruitmentTagFixture.createRecruitmentTag(recruitmentPost, tag);

        // when, then
        Assertions.assertThatThrownBy(() -> recruitmentTagRepository.saveAndFlush(recruitmentTag)).isInstanceOf(
                DataIntegrityViolationException.class);
    }

    @Test
    void 구인태그저장_예외발생_tag가null인경우() {
        // given
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(1L).orElse(null);
        Tag tag = null;
        RecruitmentTag recruitmentTag = RecruitmentTagFixture.createRecruitmentTag(recruitmentPost, tag);

        // when, then
        Assertions.assertThatThrownBy(() -> recruitmentTagRepository.saveAndFlush(recruitmentTag)).isInstanceOf(
                DataIntegrityViolationException.class);
    }

    @Test
    void 구인태그저장_예외발생_name글자수가15자넘는경우() {
        // given
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(1L).orElse(null);
        Tag tag = TagFixture.createCoureNanmeTag(NAME_EXCEED_15);
        RecruitmentTag recruitmentTag = RecruitmentTagFixture.createRecruitmentTag(recruitmentPost, tag);

        // when, then
        Assertions.assertThatThrownBy(() -> recruitmentTagRepository.saveAndFlush(recruitmentTag)).isInstanceOf(
                InvalidDataAccessApiUsageException.class);
    }

    @Test
    @DisplayName("해당 메서드는 페치 조인을 사용한다.")
    void 구인태그조회_구인태그반환() {
        // given

        // when
        List<RecruitmentTag> recruitmentTags = recruitmentTagRepository.findByPostIdWithTag(1L);

        // then
        for (RecruitmentTag tag : recruitmentTags) {
            assertThat(tag.getRecruitmentPost().getId()).isEqualTo(1L);
            assertThat(tag.getTag()).isNotNull();
        }
    }
}
