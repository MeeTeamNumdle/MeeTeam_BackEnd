package synk.meeteam.domain.recruitment.recruitment_post.api;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.CreateRecruitmentPostResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentPostApplyInfoResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.GetRecruitmentRoleResponseDto;
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

    @GetMapping("/apply")
    @Override
    public ResponseEntity<GetRecruitmentPostApplyInfoResponseDto> getApplyInfo(@AuthUser User user) {
        List<GetRecruitmentRoleResponseDto> recruitmentRoles = new ArrayList<>();
        recruitmentRoles.add(new GetRecruitmentRoleResponseDto(1L, "백엔드개발자"));
        recruitmentRoles.add(new GetRecruitmentRoleResponseDto(2L, "프론트엔드개발자"));

        return ResponseEntity.ok()
                .body(new GetRecruitmentPostApplyInfoResponseDto("송민규", 4.43, "명문대학교",
                        "명품학과", 2018, "miekkse@kw.kr", recruitmentRoles));
    }

}
