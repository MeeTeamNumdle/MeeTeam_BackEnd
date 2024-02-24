package synk.meeteam.domain.common.university.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import synk.meeteam.domain.common.university.dto.response.GetUniversityDto;

@Tag(name = "university", description = "대학교 관련 API")
public interface UniversityApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200")
            }
    )
    @Operation(summary = "대학교 목록 조회 API")
    @SecurityRequirements
    ResponseEntity<List<GetUniversityDto>> getUniversities();
}
