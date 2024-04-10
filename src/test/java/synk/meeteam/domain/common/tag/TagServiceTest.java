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

}
