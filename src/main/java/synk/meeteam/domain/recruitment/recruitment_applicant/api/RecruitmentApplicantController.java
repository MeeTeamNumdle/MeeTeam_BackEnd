package synk.meeteam.domain.recruitment.recruitment_applicant.api;


import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.request.ProcessApplicantRequestDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.request.SetLinkRequestDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetRecruitmentRoleStatusResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetRoleDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.facade.RecruitmentApplicantFacade;
import synk.meeteam.domain.recruitment.recruitment_applicant.service.RecruitmentApplicantService;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment/applicants")
public class RecruitmentApplicantController implements RecruitmentApplicantApi {

    private final RecruitmentApplicantFacade recruitmentApplicantFacade;

    private final RecruitmentApplicantService recruitmentApplicantService;
    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;

    private final UserService userService;

    @PutMapping("/{id}/link")
    @Override
    public ResponseEntity<Void> setLink(@PathVariable("id") Long postId,
                                        @Valid @RequestBody SetLinkRequestDto requestDto, @AuthUser User user) {

        recruitmentPostService.setLink(postId, requestDto.link(), user.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/info")
    @Override
    public ResponseEntity<GetApplicantInfoResponseDto> getApplyInfo(@PathVariable("id") Long postId,
                                                                    @AuthUser User user) {
        RecruitmentPost recruitmentPost = recruitmentPostService.getRecruitmentPost(postId);
        List<RecruitmentRole> applyStatusRecruitmentRoles = recruitmentRoleService.findApplyStatusRecruitmentRole(
                postId);

        List<GetRecruitmentRoleStatusResponseDto> roleStatusResponseDtos = applyStatusRecruitmentRoles.stream()
                .map(role -> GetRecruitmentRoleStatusResponseDto.of(role.getRole().getName(), role.getCount(),
                        role.getRecruitedCount()))
                .toList();

        List<GetRoleDto> roleDtos = applyStatusRecruitmentRoles.stream()
                .map(role -> new GetRoleDto(role.getRole().getId(), role.getRole().getName()))
                .toList();

        return ResponseEntity.ok()
                .body(new GetApplicantInfoResponseDto(recruitmentPost.getTitle(), recruitmentPost.getKakaoLink(),
                        user.isFirstApplicantAccess(),roleStatusResponseDtos,
                        roleDtos));
    }

    @PatchMapping("/{id}/access")
    @Override
    public ResponseEntity<Void> processFirstAccess(@PathVariable("id") Long postId, @AuthUser User user) {
        userService.processFirstAccess(user);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/approve")
    @Override
    public ResponseEntity<Void> approveApplicant(@PathVariable("id") Long postId,
                                                 @RequestBody ProcessApplicantRequestDto requestDto,
                                                 @AuthUser User user) {

        // recruitmentPost 의 responseCount + 1
        // recruitmentRole 의 recruitedCount + 1 (거절의 경우 변화X)
        // applicant의 enum 상태 변경

        recruitmentApplicantFacade.approveApplicant(postId, user.getId(), requestDto.applicantIds());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/reject")
    @Override
    public ResponseEntity<Void> rejectApplicants(@PathVariable("id") Long postId,
                                                 @RequestBody ProcessApplicantRequestDto requestDto,
                                                 @AuthUser User user) {

        recruitmentApplicantFacade.rejectApplicants(postId, user.getId(), requestDto.applicantIds());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GetApplicantResponseDto> getApplicants(@PathVariable("id") Long postId,
                                                                 @RequestParam(value = "role", required = false) Long roleId,
                                                                 @RequestParam(name = "page", defaultValue = "1") int page,
                                                                 @RequestParam(name = "size", defaultValue = "8") int size,
                                                                 @AuthUser User user) {

        GetApplicantResponseDto responseDtos = recruitmentApplicantService.getAllByRole(postId, roleId, page, size);
        return ResponseEntity.ok().body(responseDtos);
    }

}
