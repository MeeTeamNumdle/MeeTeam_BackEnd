package synk.meeteam.domain.common.tag;

import static synk.meeteam.domain.common.tag.TagFixture.NAME_EXCEED_15;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.common.tag.repository.TagRepository;

@DataJpaTest
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    void 태그생성_생성된태그반환() {
        // given
        Tag tag = TagFixture.createCoureNanmeTag("오픈소스프로젝트");

        // when
        Tag savedTag = tagRepository.save(tag);

        // then
        Assertions.assertThat(savedTag)
                .extracting("name", "type")
                .containsExactly(tag.getName(), tag.getType());
    }

    @Test
    void 태그생성_예외발생_TagType이null인경우() {
        // given
        Tag tag = TagFixture.createTag("오픈소스프로젝트", null);

        // when, then
        Assertions.assertThatThrownBy(() -> tagRepository.save(tag))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void 태그생성_예외발생_name이null인경우() {
        // given
        Tag tag = TagFixture.createTag(null, TagType.PROFESSOR);

        // when, then
        Assertions.assertThatThrownBy(() -> tagRepository.save(tag))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void 특정태그조회_특정태그반환_존재하는태그입력일경우() {
        // given
        Long existTagId = 1L;

        // when
        Tag tag = tagRepository.findById(existTagId).orElse(null);

        // then
        Assertions.assertThat(tag)
                .extracting("name", "type")
                .containsExactly(tag.getName(), tag.getType());
    }

    @Test
    void 특정태그조회_null반환_존재하지않는태그입력일경우() {
        // given
        Long existTagId = 100L;

        // when
        Tag tag = tagRepository.findById(existTagId).orElse(null);

        // then
        Assertions.assertThat(tag).isNull();
    }

    @Test
    void 특정태그조회_예외반환_입력이null인경우() {
        // given
        Long existTagId = null;

        // when, then
        Assertions.assertThatThrownBy(() -> tagRepository.findById(existTagId)).isInstanceOf(
                InvalidDataAccessApiUsageException.class);
    }

    @Test
    void 태그생성_예외발생_name글자수가15자넘는경우() {
        // given
        Tag tag = TagFixture.createCoureNanmeTag(NAME_EXCEED_15);

        // when, then
        Assertions.assertThatThrownBy(() -> tagRepository.saveAndFlush(tag)).isInstanceOf(
                DataIntegrityViolationException.class);
    }
}
