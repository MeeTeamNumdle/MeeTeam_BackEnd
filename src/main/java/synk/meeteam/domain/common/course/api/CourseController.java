package synk.meeteam.domain.common.course.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.course.dto.SearchCourseDto;
import synk.meeteam.domain.common.course.service.CourseService;
import synk.meeteam.domain.common.course.service.ProfessorService;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseApi {

    private final CourseService courseService;
    private final ProfessorService professorService;

    @GetMapping("/course/search")
    @Override
    public ResponseEntity<List<SearchCourseDto>> searchCourse(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {

        List<SearchCourseDto> course = courseService.searchByKeyword(keyword, limit);

        return ResponseEntity.ok(course);
    }

    @GetMapping("/professor/search")
    @Override
    public ResponseEntity<List<SearchCourseDto>> searchProfessor(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "limit", required = false, defaultValue = "5") long limit) {
        List<SearchCourseDto> professors = professorService.searchByKeyword(keyword, limit);

        return ResponseEntity.ok(professors);
    }
}
