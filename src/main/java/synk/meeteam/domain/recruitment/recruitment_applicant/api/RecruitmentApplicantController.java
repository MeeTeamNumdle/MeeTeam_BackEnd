package synk.meeteam.domain.recruitment.recruitment_applicant.api;


import jakarta.validation.Valid;
import java.util.ArrayList;
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
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment/applicant")
public class RecruitmentApplicantController implements RecruitmentApplicantApi {

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
        List<GetRecruitmentRoleStatusResponseDto> recruitmentRoleStatus = new ArrayList<>();
        recruitmentRoleStatus.add(new GetRecruitmentRoleStatusResponseDto("백엔드개발자", 3L, 1L));
        recruitmentRoleStatus.add(new GetRecruitmentRoleStatusResponseDto("프론트엔드개발자", 5L, 2L));

        List<RoleDto> roles = new ArrayList<>();
        roles.add(new RoleDto(1L, "백엔드개발자"));
        roles.add(new RoleDto(2L, "프론트엔드개발자"));

        return ResponseEntity.ok()
                .body(new GetApplicantInfoResponseDto("구인글제목입니다", "카카오톡 오픈채팅방링크입니다.", recruitmentRoleStatus, roles));
    }

}
