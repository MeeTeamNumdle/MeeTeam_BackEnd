package synk.meeteam.domain.recruitment.recruitment_applicant.api;


import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.role.dto.RoleDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.request.SetLinkRequestDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetRecruitmentRoleStatusResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role.service.RecruitmentRoleService;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment/applicant")
public class RecruitmentApplicantController implements RecruitmentApplicantApi {

    private final RecruitmentPostService recruitmentPostService;
    private final RecruitmentRoleService recruitmentRoleService;

    @PutMapping("{id}/link")
    @Override
    public ResponseEntity<Void> setLink(@PathVariable("id") Long postId,
                                        @Valid @RequestBody SetLinkRequestDto requestDto) {

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

        List<RoleDto> roleDtos = applyStatusRecruitmentRoles.stream()
                .map(role -> new RoleDto(role.getRole().getId(), role.getRole().getName()))
                .toList();

        return ResponseEntity.ok()
                .body(new GetApplicantInfoResponseDto(recruitmentPost.getTitle(), recruitmentPost.getKakaoLink(),
                        roleStatusResponseDtos, roleDtos));
    }

}
