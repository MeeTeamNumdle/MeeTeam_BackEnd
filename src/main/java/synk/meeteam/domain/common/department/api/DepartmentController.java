package synk.meeteam.domain.common.department.api;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.department.dto.GetDepartmentResponseDto;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController implements DepartmentApi {
    @GetMapping
    @Override
    public ResponseEntity<List<GetDepartmentResponseDto>> getDepartments(
            @RequestParam("university") Long universityId) {
        return null;
    }
}
