package synk.meeteam.domain.user.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.portfolio.portfolio.service.PortfolioService;
import synk.meeteam.domain.user.award.service.AwardService;
import synk.meeteam.domain.user.user.dto.UpdateProfileCommandMapper;
import synk.meeteam.domain.user.user.dto.request.UpdateProfileRequestDto;
import synk.meeteam.domain.user.user.dto.response.CheckDuplicateNicknameResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.domain.user.user_link.service.UserLinkService;
import synk.meeteam.domain.user.user_skill.service.UserSkillService;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserApi {

    private final UserService userService;
    private final UserLinkService userLinkService;
    private final UserSkillService userSkillService;
    private final PortfolioService portfolioService;
    private final AwardService awardService;

    private final UpdateProfileCommandMapper updateProfileCommandMapper;

    @Override
    @PutMapping("/profile")
    public ResponseEntity<String> saveProfile(@AuthUser User user,
                                              @Valid @RequestBody UpdateProfileRequestDto requestDto) {

        //유저 닉네임 덮어쓰기
        userService.updateNickname(user, requestDto.nickname());

        //프로필 정보 업데이트
        //현재 email 추가 안됨 고민 필요

        userService.updateProfile(user, updateProfileCommandMapper.toUpdateProfileCommand(requestDto));

        //유저 링크 덮어쓰기
        userLinkService.updateUserLinks(user, requestDto.links());

        //유저 스킬 덮어쓰기
        userSkillService.updateUserSkillsByIds(user, requestDto.skills());

        //수상 활동 내역 덮어쓰기
        awardService.updateAward(user, requestDto.awards());

        //핀 설정
        portfolioService.updatePinPortfoliosByIds(user, requestDto.portfolios());

        return ResponseEntity.ok().body(user.getEncryptUserId());
    }


    @Override
    @GetMapping("/search/check-duplicate")
    public ResponseEntity<CheckDuplicateNicknameResponseDto> checkDuplicateNickname(
            @RequestParam("nickname") String nickname) {
        boolean available = userService.checkAvailableNickname(nickname);

        return ResponseEntity.ok(CheckDuplicateNicknameResponseDto.of(available));
    }
}