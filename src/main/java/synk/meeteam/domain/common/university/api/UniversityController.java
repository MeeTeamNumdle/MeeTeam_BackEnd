package synk.meeteam.domain.common.university.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.university.dto.response.GetUniversityListDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/university")
public class UniversityController implements UniversityApi {

    @Override
    @GetMapping
    public ResponseEntity<GetUniversityListDto> getUniversities() {
        return null;
    }
}
