package synk.meeteam.domain.common.department.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import synk.meeteam.domain.common.department.dto.GetDepartmentResponseDto;

@Tag(name = "department", description = "학과 관련 API")
public interface DepartmentApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "학과 조회 성공"),
            }
    )
    @Operation(summary = "특정 학교의 학과 조회 API")
    @SecurityRequirements
    ResponseEntity<List<GetDepartmentResponseDto>> getDepartments(@RequestParam("university-id") Long universityId);
}
