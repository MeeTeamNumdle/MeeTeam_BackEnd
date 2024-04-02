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
}
