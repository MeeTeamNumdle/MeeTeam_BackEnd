package synk.meeteam.domain.recruitment.recruitment_tag;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static synk.meeteam.domain.common.tag.TagFixture.NAME_EXCEED_15;
import static synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture.TITLE;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import synk.meeteam.domain.common.tag.TagFixture;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.common.tag.repository.TagRepository;
import synk.meeteam.domain.recruitment.recruitment_post.RecruitmentPostFixture;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;
import synk.meeteam.domain.recruitment.recruitment_tag.repository.RecruitmentTagRepository;
import synk.meeteam.domain.recruitment.recruitment_tag.service.RecruitmentTagService;
import synk.meeteam.domain.recruitment.recruitment_tag.service.vo.RecruitmentTagVO;

@ExtendWith(MockitoExtension.class)
public class RecruitmentTagServiceTest {
    @InjectMocks
    private RecruitmentTagService recruitmentTagService;

    @Mock
    private RecruitmentTagRepository recruitmentTagRepository;

    @Mock
    private TagRepository tagRepository;

    @Test
    void 구인태그조회및저장_생성된구인태그반환_새로운태그입력일경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Tag tag = TagFixture.createCoureNanmeTag("이름");
        RecruitmentTag recruitmentTag = RecruitmentTagFixture.createRecruitmentTag(recruitmentPost, tag);

        doReturn(Optional.ofNullable(null)).when(tagRepository).findByName(tag.getName());
        doReturn(tag).when(tagRepository).save(tag);
        doReturn(recruitmentTag).when(recruitmentTagRepository).save(recruitmentTag);

        // when
        RecruitmentTag savedRecruitmentTag = recruitmentTagService.createRecruitmentTag(recruitmentTag);

        // then
        Assertions.assertThat(savedRecruitmentTag)
                .extracting("recruitmentPost", "tag")
                .containsExactly(recruitmentPost, tag);
    }

    @Test
    void 구인태그조회및저장_기존에저장된구인태그반환_이미존재하는태그입력일경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Tag tag = TagFixture.createCoureNanmeTag("이름");
        RecruitmentTag recruitmentTag = RecruitmentTagFixture.createRecruitmentTag(recruitmentPost, tag);

        doReturn(Optional.ofNullable(tag)).when(tagRepository).findByName(tag.getName());
        doReturn(recruitmentTag).when(recruitmentTagRepository).save(recruitmentTag);

        // when
        RecruitmentTag savedRecruitmentTag = recruitmentTagService.createRecruitmentTag(recruitmentTag);

        // then
        Assertions.assertThat(savedRecruitmentTag)
                .extracting("recruitmentPost", "tag")
                .containsExactly(recruitmentPost, tag);
    }

    @Test
    void 구인태그조회및저장_예외발생_recruitmentPost가null인경우() {
        // given
        RecruitmentPost recruitmentPost = null;
        Tag tag = TagFixture.createCoureNanmeTag("이름");
        RecruitmentTag recruitmentTag = RecruitmentTagFixture.createRecruitmentTag(recruitmentPost, tag);

        doReturn(Optional.ofNullable(tag)).when(tagRepository).findByName(tag.getName());
        doThrow(DataIntegrityViolationException.class).when(recruitmentTagRepository).save(recruitmentTag);

        // when, then
        Assertions.assertThatThrownBy(() -> recruitmentTagService.createRecruitmentTag(recruitmentTag)).isInstanceOf(
                DataIntegrityViolationException.class);
    }

    @Test
    void 구인태그조회및저장_예외발생_name글자수가15자넘는경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Tag tag = TagFixture.createCoureNanmeTag(NAME_EXCEED_15);
        RecruitmentTag recruitmentTag = RecruitmentTagFixture.createRecruitmentTag(recruitmentPost, tag);

        doReturn(Optional.ofNullable(null)).when(tagRepository).findByName(tag.getName());
        doThrow(InvalidDataAccessApiUsageException.class).when(tagRepository).save(tag);

        // when, then
        Assertions.assertThatThrownBy(() -> recruitmentTagService.createRecruitmentTag(recruitmentTag)).isInstanceOf(
                InvalidDataAccessApiUsageException.class);
    }

    @Test
    @DisplayName("해당 메서드는 페치 조인을 사용한다.")
    void 구인태그조회_구인태그및수업태그반환_수업관련구인글경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Tag tag = TagFixture.createTag("대학생", TagType.MEETEAM);
        doReturn(RecruitmentTagFixture.createRecruitmentTags(recruitmentPost, tag)).when(recruitmentTagRepository)
                .findByPostIdWithTag(1L);

        // when
        RecruitmentTagVO recruitmentTagVO = recruitmentTagService.findByRecruitmentPostId(1L);

        // then
        Assertions.assertThat(recruitmentTagVO)
                .extracting("courseName", "courseProfessor")
                .containsExactly("응소실", "김용혁");
        Assertions.assertThat(recruitmentTagVO.recruitmentTags().get(0))
                .extracting("recruitmentPost", "tag")
                .containsExactly(recruitmentPost, tag);
    }

    @Test
    void 구인태그조회_구인태그반환_수업관련구인글아닌경우() {
        // given
        RecruitmentPost recruitmentPost = RecruitmentPostFixture.createRecruitmentPost(TITLE);
        Tag tag = TagFixture.createTag("대학생", TagType.MEETEAM);
        doReturn(RecruitmentTagFixture.createRecruitmentTag(recruitmentPost, tag)).when(recruitmentTagRepository)
                .findByPostIdWithTag(1L);

        // when
        RecruitmentTagVO recruitmentTagVO = recruitmentTagService.findByRecruitmentPostId(1L);

        // then
        Assertions.assertThat(recruitmentTagVO.recruitmentTags().get(0))
                .extracting("recruitmentPost", "tag")
                .containsExactly(recruitmentPost, tag);
    }

}
