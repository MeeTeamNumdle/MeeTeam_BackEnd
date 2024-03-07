package synk.meeteam.domain.user.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.user.user.dto.request.UpdateProfileRequestDto;
import synk.meeteam.domain.user.user.dto.response.CheckDuplicateNicknameResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @PutMapping("/profile")
    public ResponseEntity<Void> saveProfile(@AuthUser User user, @RequestBody UpdateProfileRequestDto requestDto) {
        return ResponseEntity.ok().build();
    }


    @Override
    @GetMapping("/search/check-duplicate")
    public ResponseEntity<CheckDuplicateNicknameResponseDto> checkDuplicateNickname(
            @RequestParam("nickname") String nickname) {
        boolean available = userService.checkDuplicateNickname(nickname);

        return ResponseEntity.ok(CheckDuplicateNicknameResponseDto.of(available));
    }
}