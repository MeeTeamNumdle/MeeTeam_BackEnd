package synk.meeteam.domain.common.university.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import synk.meeteam.domain.common.university.dto.response.GetUniversityListDto;

@Tag(name = "university", description = "대학교 관련 API")
public interface UniversityApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "대학교 목록 조회에 성공했습니다."),
                    @ApiResponse(responseCode = "500", description = "서버 에러입니다.", content = @Content)
            }
    )
    @Operation(summary = "대학교 목록 조회", description = "대학교 목록이 조회된다.")
    ResponseEntity<GetUniversityListDto> getUniversities();
}
