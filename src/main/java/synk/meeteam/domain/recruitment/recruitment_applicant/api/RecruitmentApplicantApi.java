package synk.meeteam.domain.recruitment.recruitment_applicant.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.request.ApproveApplicantRequestDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.request.ProcessApplicantRequestDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.request.SetLinkRequestDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;

@Tag(name = "applicant", description = "신청자 관리 관련 API")
public interface RecruitmentApplicantApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "오픈카톡방 링크 설정 성공"),
            }
    )
    @Operation(summary = "오픈카톡 링크 설정(수정) API")
    ResponseEntity<Void> setLink(@PathVariable("id") Long postId, @Valid @RequestBody SetLinkRequestDto requestDto,
                                 @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "신청자 목록 조회 성공"),
            }
    )
    @Operation(summary = "신청 관리 정보 조회 API")
    ResponseEntity<GetApplicantInfoResponseDto> getApplyInfo(@PathVariable("id") Long postId, @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "신청자 승인 성공"),
            }
    )
    @Operation(summary = "신청자 승인 API")
    ResponseEntity<Void> approveApplicant(@PathVariable("id") Long postId,
                                          @RequestBody ApproveApplicantRequestDto requestDto,
                                          @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "신청자 거절 성공"),
            }
    )
    @Operation(summary = "신청자 거절 API")
    ResponseEntity<Void> rejectApplicants(@PathVariable("id") Long postId,
                                          @RequestBody ProcessApplicantRequestDto requestDto,
                                          @AuthUser User user);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "신청자 목록 조회 성공"),
            }
    )
    @Operation(summary = "신청자 목록 조회 API")
    ResponseEntity<List<GetApplicantResponseDto>> getApplicants(@PathVariable("id") Long postId,
                                                                @RequestParam(value = "role", required = false) Long roleId,
                                                                @AuthUser User user);
}
