package synk.meeteam.domain.recruitment.recruitment_post.api;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.applyRecruitmentRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetApplyInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetApplyRecruitmentRoleResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentPostResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.security.AuthUser;


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

    @GetMapping
    @Override
    public ResponseEntity<GetRecruitmentPostResponseDto> getRecruitmentPost(@Valid @RequestParam Long id,
                                                                            @AuthUser User user) {
        return null;
    }

    @GetMapping("/apply")
    @Override
    public ResponseEntity<GetApplyInfoResponseDto> getApplyInfo(@AuthUser User user) {
        List<GetApplyRecruitmentRoleResponseDto> recruitmentRoles = new ArrayList<>();
        recruitmentRoles.add(new GetApplyRecruitmentRoleResponseDto(1L, "백엔드개발자"));
        recruitmentRoles.add(new GetApplyRecruitmentRoleResponseDto(2L, "프론트엔드개발자"));

        return ResponseEntity.ok()
                .body(new GetApplyInfoResponseDto("송민규", 4.43, "명문대학교",
                        "명품학과", 2018, "miekkse@kw.kr", recruitmentRoles));
    }


    @PostMapping("/apply")
    @Override
    public ResponseEntity<Void> applyRecruitment(applyRecruitmentRequestDto requestDto, User user) {

        return ResponseEntity.ok().build();
    }
}
