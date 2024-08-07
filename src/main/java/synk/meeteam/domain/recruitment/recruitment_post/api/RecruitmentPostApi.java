package synk.meeteam.domain.recruitment.recruitment_post.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.ApplyRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateCommentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.ModifyCommentRequestDto;
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
            @Valid @RequestBody CreateRecruitmentPostRequestDto requestDto, @AuthUser User user);

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
                    @ApiResponse(responseCode = "200", description = "구인 신청 취소 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "구인 신청 취소 API", tags = {"recruitment"})
    ResponseEntity<Void> cancelApplyRecruitment(@PathVariable("id") Long postId, @AuthUser User user);

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
            @RequestParam(value = "size", required = false, defaultValue = "24") @Valid @Min(1) int size,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Valid @Min(1) int page,
            @RequestParam(value = "field", required = false) Long fieldId,
            @RequestParam(value = "scope", required = false) Integer scopeOrdinal,
            @RequestParam(value = "category", required = false) Integer categoryOrdinal,
            @RequestParam(value = "skill", required = false) List<Long> skillIds,
            @RequestParam(value = "role", required = false) List<Long> roleIds,
            @RequestParam(value = "tag", required = false) List<Long> tagIds,
            @RequestParam(value = "course", required = false) Long courseId,
            @RequestParam(value = "professor", required = false) Long professorId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @AuthUser User user
    );


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

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "댓글 삭제 API", tags = {"comment"})
    ResponseEntity<Void> deleteComment(@PathVariable("id") Long postId,
                                       @PathVariable("comment-id") Long commentId, @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"
                            , content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE)
                    })
            }
    )
    @Operation(summary = "댓글 수정 API", tags = {"comment"})
    ResponseEntity<Void> modifyComment(@PathVariable("id") Long postId,
                                       @Valid @RequestBody ModifyCommentRequestDto requestDto, @AuthUser User user);
}
