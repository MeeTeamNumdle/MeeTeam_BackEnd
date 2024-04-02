package synk.meeteam.domain.recruitment.recruitment_applicant.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.request.SetLinkRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.service.RecruitmentPostService;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment/applicant")
public class RecruitmentApplicantController implements RecruitmentApplicantApi {

    private final RecruitmentPostService recruitmentPostService;

    @PutMapping("/{id}/link")
    @Override
    public ResponseEntity<Void> setLink(@PathVariable("id") Long postId,
                                        @Valid @RequestBody SetLinkRequestDto requestDto, @AuthUser User user) {

        recruitmentPostService.setLink(postId, requestDto.link(), user.getId());

        return ResponseEntity.ok().build();
    }
}
