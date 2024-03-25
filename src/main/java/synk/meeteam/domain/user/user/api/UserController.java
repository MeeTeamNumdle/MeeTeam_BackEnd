package synk.meeteam.domain.user.user.api;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;
import synk.meeteam.domain.user.user.dto.request.UpdateProfileRequestDto;
import synk.meeteam.domain.user.user.dto.response.CheckDuplicateNicknameResponseDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileAwardDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileEmailDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileLinkDto;
import synk.meeteam.domain.user.user.dto.response.GetProfilePhoneDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.ProfileFacade;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserApi {

    private final UserService userService;
    private final ProfileFacade profileFacade;

    @Override
    @PutMapping("/profile")
    public ResponseEntity<String> editProfile(@AuthUser User user,
                                              @Valid @RequestBody UpdateProfileRequestDto requestDto) {

        profileFacade.editProfile(user, requestDto);

        return ResponseEntity.ok().body(user.getEncryptUserId());
    }

    @Override
    @GetMapping("/profile/{userId}")
    public ResponseEntity<GetProfileResponseDto> getProfile(@PathVariable String userId) {
        GetProfileResponseDto responseDto = new GetProfileResponseDto(
                "https://~~",
                "민지",
                "mingi123",
                true,
                "백엔드 개발",
                "한줄소개입니다.",
                "<h1>자기소개입니다.</h1></br>안녕하세요. 저는 백엔드개발을 합니다.",
                new GetProfileEmailDto("minji@kw.ac.kr", true, true),
                new GetProfileEmailDto("mingi@naver.com", true, false),
                new GetProfilePhoneDto("010-1234-5678", true),
                "광운대학교",
                "소프트웨어학부",
                4.5,
                4.3,
                2019,
                List.of(
                        new GetProfilePortfolioDto(1L, "Meeteam 팀을 만나다", "https://~",
                                LocalDate.of(2023, 6, 11), LocalDate.of(2024, 3, 25), "개발", "백엔드개발자",
                                List.of(new SkillDto(1L, "스프링"), new SkillDto(2L, "깃")))
                ),
                List.of(
                        new GetProfileLinkDto("https://~~", "Link"),
                        new GetProfileLinkDto("https://~~", "Github")
                ),
                List.of(new GetProfileAwardDto(
                        "공공데이터 공모전", LocalDate.of(2023, 6, 11), LocalDate.of(2024, 3, 25), "장려상 수상"
                )),
                List.of(new SkillDto(1L, "스프링"), new SkillDto(2L, "깃"))
        );
        return ResponseEntity.ok(responseDto);
    }


    @Override
    @GetMapping("/search/check-duplicate")
    public ResponseEntity<CheckDuplicateNicknameResponseDto> checkDuplicateNickname(
            @RequestParam("nickname") String nickname) {
        boolean available = userService.checkAvailableNickname(nickname);

        return ResponseEntity.ok(CheckDuplicateNicknameResponseDto.of(available));
    }
}