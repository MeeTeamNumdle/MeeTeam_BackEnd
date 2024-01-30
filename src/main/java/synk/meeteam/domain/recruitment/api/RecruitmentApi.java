package synk.meeteam.domain.recruitment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import synk.meeteam.domain.recruitment.dto.request.CreateRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.dto.response.CreateRecruitmentResponseDto;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.security.AuthUser;

@Tag(name = "Recruitment", description = "구인과 관련된 API")
public interface RecruitmentApi {

    @SecurityRequirement(name = "Authorization")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "구인글 생성에 성공하였습니다."),
            }
    )
    @Operation(summary = "새 구인글 생성")
    ResponseEntity<CreateRecruitmentResponseDto> createRecruitment(
            @RequestBody @Valid final CreateRecruitmentRequestDto requestDto, @AuthUser User user);

}
