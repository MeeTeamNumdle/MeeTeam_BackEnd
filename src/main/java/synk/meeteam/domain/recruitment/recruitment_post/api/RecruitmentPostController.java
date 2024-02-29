package synk.meeteam.domain.recruitment.recruitment_post.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.recruitment.recruitment_post.dto.RecruitmentPostMapper;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_post.facade.RecruitmentPostFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment/post")
public class RecruitmentPostController implements RecruitmentPostApi {

    private final RecruitmentPostFacade recruitmentPostFacade;
    private final RecruitmentPostMapper recruitmentPostMapper;

    @PostMapping
    @Override
    public ResponseEntity<CreateRecruitmentPostResponseDto> createRecruitmentPost(
            @Valid @RequestBody CreateRecruitmentPostRequestDto requestDto) {

        // field 가져오기 (아직 개발 X)
        Field tmpField = new Field(1L, "개발");

        RecruitmentPost recruitmentPost = recruitmentPostMapper.toRecruitmentEntity(requestDto, tmpField);

        // null 부분은 아직 개발 X
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateRecruitmentPostResponseDto.from(
                recruitmentPostFacade.createRecruitmentPost(recruitmentPost, null, null, null)));
    }
}
