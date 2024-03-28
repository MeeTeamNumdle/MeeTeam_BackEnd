package synk.meeteam.domain.recruitment.recruitment_post.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.ApplyRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateCommentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.SetOpenKakaoLinkRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetApplyInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.PaginationSearchPostResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;

// @Tags({@Tag(name = "recruitment", description = "구인 관련 API"), @Tag(name = "comment", description = "댓글 관련 API")})
public interface RecruitmentPostApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "구인글 생성 성공"),
            }
    )
    @Operation(summary = "구인글 생성 API", tags = {"recruitment"})
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
    @Operation(summary = "특정 구인글 조회 API", tags = {"recruitment"})
    @SecurityRequirements
    ResponseEntity<GetRecruitmentPostResponseDto> getRecruitmentPost(
            @PathVariable("id") Long postId, @AuthUser User user);


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "신청 정보 조회 성공"),
            }
    )
    @Operation(summary = "신청 정보 조회 API", tags = {"recruitment"})
    ResponseEntity<GetApplyInfoResponseDto> getApplyInfo(@PathVariable("id") Long postId,
                                                         @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "구인 신청 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "구인 신청 API", tags = {"recruitment"})
    ResponseEntity<Void> applyRecruitment(@PathVariable("id") Long postId,
                                          @Valid @RequestBody ApplyRecruitmentRequestDto requestDto,
                                          @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "구인 마감 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "구인 마감 API", tags = {"recruitment"})
    ResponseEntity<Void> closeRecruitment(@PathVariable("id") Long postId, @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "구인 수정 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "구인글 수정 API", tags = {"recruitment"})
    ResponseEntity<Void> modifyRecruitmentPost(@Valid @RequestBody CreateRecruitmentPostRequestDto requestDto,
                                               @PathVariable("id") Long postId, @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "북마크 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "구인글 북마크 API", tags = {"recruitment"})
    ResponseEntity<Void> bookmarkRecruitmentPost(@PathVariable("id") Long postId, @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "북마크 취소 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "구인글 북마크 취소 API", tags = {"recruitment"})
    ResponseEntity<Void> cancelBookmarkRecruitmentPost(@PathVariable("id") Long postId, @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "검색 성공"),
                    @ApiResponse(responseCode = "403", description = "접근 권한이 없습니다")
            }
    )
    @Operation(summary = "구인글 목록 검색 API", tags = {"recruitment"})
    ResponseEntity<PaginationSearchPostResponseDto> searchRecruitmentPost(
            @RequestParam Long fieldId,
            @RequestParam Long scopeIndex,
            @RequestParam Long categoryIndex,
            @RequestParam List<Long> skills,
            @RequestParam List<Long> roles,
            @RequestParam List<Long> tags,
            @RequestParam String keyword
    );


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "오픈 카톡방 링크 설정 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 오픈 카톡방 링크")
            }
    )
    @Operation(summary = "구인글 오픈카톡 설정 API", tags = {"applicant"})
    ResponseEntity<Void> setOpenKaKaoLink(@AuthUser User user, @PathVariable("id") Long postId,
                                          SetOpenKakaoLinkRequestDto requestDto);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "댓글 등록 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "댓글 등록 API", tags = {"comment"})
    ResponseEntity<Void> registerComment(@PathVariable("id") Long postId,
                                         @Valid @RequestBody CreateCommentRequestDto requestDto, @AuthUser User user);

}
