package synk.meeteam.domain.common.tag.api;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.tag.dto.TagDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController implements TagApi {

    @GetMapping("/search")
    @Override
    public ResponseEntity<List<TagDto>> searchTag(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {

        List<TagDto> tags = Arrays.asList(new TagDto(1L, "웹개발"), new TagDto(2L, "앱개발"));
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/search/course")
    @Override
    public ResponseEntity<List<TagDto>> searchCourse(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {

        List<TagDto> course = Arrays.asList(new TagDto(1L, "응용소프트웨어실습"), new TagDto(2L, "리눅스활용실습"));
        return ResponseEntity.ok(course);
    }

    @GetMapping("/search/professor")
    @Override
    public ResponseEntity<List<TagDto>> searchProfessor(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {
        List<TagDto> professors = Arrays.asList(new TagDto(1L, "문승현"), new TagDto(2L, "김진우"));
        return ResponseEntity.ok(professors);
    }


}
