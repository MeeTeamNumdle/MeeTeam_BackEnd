package synk.meeteam.domain.common.department.api;


import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.department.dto.GetDepartmentResponseDto;
import synk.meeteam.domain.common.department.service.DepartmentService;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.service.UniversityService;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController implements DepartmentApi {

    private final DepartmentService departmentService;
    private final UniversityService universityService;

    @GetMapping
    @Override
    public ResponseEntity<List<GetDepartmentResponseDto>> getDepartments(
            @RequestParam("university") @NotNull Long universityId) {
        University foundUniversity = universityService.getUniversity(universityId);

        return ResponseEntity.ok().body(departmentService.getDepartmentsByUniversity(foundUniversity).stream()
                .map(GetDepartmentResponseDto::of).toList());
    }
}
