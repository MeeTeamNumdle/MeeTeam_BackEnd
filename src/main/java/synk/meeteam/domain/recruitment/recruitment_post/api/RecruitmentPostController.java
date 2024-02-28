package synk.meeteam.domain.recruitment.recruitment_post.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment/post")
public class RecruitmentPostController implements RecruitmentPostApi {

    @PostMapping
    @Override
    public ResponseEntity<CreateRecruitmentPostResponseDto> createRecruitmentPost(
            @Valid CreateRecruitmentPostRequestDto requestDto) {
        return null;
    }
}
