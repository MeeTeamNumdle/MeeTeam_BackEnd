package synk.meeteam.domain.recruitment.recruitment_post.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentPostApplyInfoResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;

@Tag(name = "recruitment", description = "구인 관련 API")
public interface RecruitmentPostApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "구인글 생성 성공"),
            }
    )
    @Operation(summary = "구인글 생성 API")
    ResponseEntity<CreateRecruitmentPostResponseDto> createRecruitmentPost(
            @Valid CreateRecruitmentPostRequestDto requestDto);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "신청 정보 조회 성공"),
            }
    )
    @Operation(summary = "신청 정보 조회 API")
    ResponseEntity<GetRecruitmentPostApplyInfoResponseDto> getApplyInfo(
            @AuthUser User user);
}
