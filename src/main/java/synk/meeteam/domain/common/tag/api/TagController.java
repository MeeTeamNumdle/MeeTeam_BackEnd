package synk.meeteam.domain.common.tag.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.tag.dto.SearchTagDto;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.common.tag.service.TagService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController implements TagApi {

    private final TagService tagService;

    @GetMapping("/search")
    @Override
    public ResponseEntity<List<SearchTagDto>> searchTag(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {
        List<SearchTagDto> tags = tagService.searchByKeywordAndType(keyword, limit, TagType.MEETEAM);

        return ResponseEntity.ok(tags);
    }

    @GetMapping("/search/course")
    @Override
    public ResponseEntity<List<SearchTagDto>> searchCourse(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {

        List<SearchTagDto> course = tagService.searchByKeywordAndType(keyword, limit, TagType.COURSE);

        return ResponseEntity.ok(course);
    }

    @GetMapping("/search/professor")
    @Override
    public ResponseEntity<List<SearchTagDto>> searchProfessor(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {
        List<SearchTagDto> professors = tagService.searchByKeywordAndType(keyword, limit, TagType.PROFESSOR);

        return ResponseEntity.ok(professors);
    }


}
