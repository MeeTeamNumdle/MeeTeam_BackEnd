package synk.meeteam.domain.recruitment.recruitment_post.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.SimpleRecruitmentPostDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.dto.PaginationDto;
import synk.meeteam.security.AuthUser;

@Tag(name = "management", description = "내 구인글 관리 관련 API")
public interface PostManagementApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "구인글 조회 성공"),
            }
    )
    @Operation(summary = "북마크한 구인글 조회")
    @SecurityRequirement(name = "Authorization")
    PaginationDto<SimpleRecruitmentPostDto> getBookmarkPost(@AuthUser User user,
                                                            @RequestParam(value = "is-closed", required = false) Boolean isClosed);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "구인글 조회 성공"),
            }
    )
    @Operation(summary = "내가 신청한 구인글 조회")
    @SecurityRequirement(name = "Authorization")
    PaginationDto<SimpleRecruitmentPostDto> getAppliedPost(@AuthUser User user,
                                                           @RequestParam(value = "is-closed", required = false) Boolean isClosed);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "구인글 조회 성공"),
            }
    )
    @Operation(summary = "내가 작성한 구인글 조회")
    @SecurityRequirement(name = "Authorization")
    PaginationDto<SimpleRecruitmentPostDto> getMyPost(@AuthUser User user,
                                                      @RequestParam(value = "is-closed", required = false) Boolean isClosed);
}
