package synk.meeteam.domain.common.tag;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.tag.dto.SearchTagDto;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.common.tag.repository.TagRepository;
import synk.meeteam.domain.common.tag.service.TagService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Test
    public void 태그목록조회_태그목록DTO반환() {
        //given
        String keyword = "웹";
        long limit = 3;

        doReturn(TagFixture.createTagDtos())
                .when(tagRepository).findAllByKeywordAndTopLimitAndType(keyword, limit, TagType.MEETEAM);

        //when
        List<SearchTagDto> tagDtos = tagService.searchByKeywordAndType(keyword, limit, TagType.MEETEAM);

        //then
        assertThat(tagDtos).extracting("name").containsExactly("웹개발");
    }

    @Test
    public void 강의명태그목록조회_강의명태그목록DTO반환() {
        //given
        String keyword = "응용소프트웨어실습";
        long limit = 3;

        doReturn(TagFixture.createCourseTagDtos())
                .when(tagRepository).findAllByKeywordAndTopLimitAndType(keyword, limit, TagType.COURSE);

        //when
        List<SearchTagDto> tagDtos = tagService.searchByKeywordAndType(keyword, limit, TagType.COURSE);

        //then
        assertThat(tagDtos).extracting("name").containsExactly("응용소프트웨어실습");
    }

    @Test
    public void 교수명태그목록조회_교수명태그목록DTO반환() {
        //given
        String keyword = "문";
        long limit = 3;

        doReturn(TagFixture.createProfessorTagDtos())
                .when(tagRepository).findAllByKeywordAndTopLimitAndType(keyword, limit, TagType.PROFESSOR);

        //when
        List<SearchTagDto> tagDtos = tagService.searchByKeywordAndType(keyword, limit, TagType.PROFESSOR);

        //then
        assertThat(tagDtos).extracting("name").containsExactly("문승현");
    }

}
