package synk.meeteam.domain.common.tag;

import static org.assertj.core.api.Assertions.assertThat;
import static synk.meeteam.domain.common.tag.TagFixture.NAME_EXCEED_15;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.tag.dto.SearchTagDto;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.common.tag.repository.TagRepository;

@DataJpaTest
@ActiveProfiles("test")
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    void 태그생성_생성된태그반환() {
        // given
        Tag tag = TagFixture.createCoureNameTag("오픈소스프로젝트");

        // when
        Tag savedTag = tagRepository.save(tag);

        // then
        assertThat(savedTag)
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
        assertThat(tag)
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
        assertThat(tag).isNull();
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
        Tag tag = TagFixture.createCoureNameTag(NAME_EXCEED_15);

        // when, then
        Assertions.assertThatThrownBy(() -> tagRepository.saveAndFlush(tag)).isInstanceOf(
                DataIntegrityViolationException.class);
    }

    @Test
    void 태그목록조회_태그DTO반환_keyword는공백일때() {
        //given
        String keyword = "";
        long limit = 5;
        //when
        List<SearchTagDto> tags = tagRepository.findAllByKeywordAndTopLimitAndType(keyword,
                limit, TagType.MEETEAM);

        //then
        assertThat(tags).extracting("name").containsExactlyInAnyOrder(
                "웹개발", "앱개발", "IOS개발", "블록체인개발", "안드로이드개발"
        );
        assertThat(tags.size()).isEqualTo(5);
    }

    @Test
    void 태그목록조회_태그DTO반환_keyword는웹개발일때() {
        //given
        String keyword = "웹개발";
        long limit = 5;
        //when
        List<SearchTagDto> tags = tagRepository.findAllByKeywordAndTopLimitAndType(keyword,
                limit, TagType.MEETEAM);

        //then
        assertThat(tags).extracting("name").contains("웹개발");
        assertThat(tags.size()).isEqualTo(1);
    }

}
