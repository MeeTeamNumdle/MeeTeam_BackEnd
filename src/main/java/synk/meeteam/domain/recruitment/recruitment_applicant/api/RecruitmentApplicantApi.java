package synk.meeteam.domain.recruitment.recruitment_applicant.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.request.SetLinkRequestDto;
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
}
