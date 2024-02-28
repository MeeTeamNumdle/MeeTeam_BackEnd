package synk.meeteam.domain.common.university.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.university.dto.response.UniversityDto;
import synk.meeteam.domain.common.university.service.UniversityService;

@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController implements UniversityApi {

    private final UniversityService universityService;

    @GetMapping
    @Override
    public ResponseEntity<List<UniversityDto>> getUniversities() {
        List<UniversityDto> list = universityService.getUniversities().stream().map(UniversityDto::of).toList();
        return ResponseEntity.ok(list);
    }
}
