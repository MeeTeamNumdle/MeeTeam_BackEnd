package synk.meeteam.domain.recruitment.recruitment_post.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.applyRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetApplyInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentPostResponseDto;
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
            @Valid @RequestBody CreateRecruitmentPostRequestDto requestDto);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "구인글 조회 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetRecruitmentPostResponseDto.class))
                    })
            }
    )
    @Operation(summary = "특정 구인글 조회 API")
    @SecurityRequirements
    ResponseEntity<GetRecruitmentPostResponseDto> getRecruitmentPost(
            @Valid @RequestParam Long id, @AuthUser User user);


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "신청 정보 조회 성공"),
            }
    )
    @Operation(summary = "신청 정보 조회 API")
    ResponseEntity<GetApplyInfoResponseDto> getApplyInfo(
            @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "구인 신청 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "구인 신청 API")
    ResponseEntity<Void> applyRecruitment(
            @Valid applyRecruitmentRequestDto requestDto, @AuthUser User user);
}
