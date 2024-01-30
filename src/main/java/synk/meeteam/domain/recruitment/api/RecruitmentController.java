package synk.meeteam.domain.recruitment.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.recruitment.dto.request.CreateRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.dto.response.CreateRecruitmentResponseDto;
import synk.meeteam.domain.recruitment.facade.RecruitmentFacade;
import synk.meeteam.domain.user.entity.User;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment")
public class RecruitmentController implements RecruitmentApi {
    private final RecruitmentFacade recruitmentFacade;

    @Override
    @PostMapping
    public ResponseEntity<CreateRecruitmentResponseDto> createRecruitment(
            @RequestBody @Valid final CreateRecruitmentRequestDto requestDto, @AuthUser User user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recruitmentFacade.createRecruitment(requestDto, user));
    }
}
