package synk.meeteam.domain.user.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.user.user.dto.response.CheckDuplicateNicknameResponseDto;
import synk.meeteam.domain.user.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    @GetMapping("/search/check-duplicate")
    public CheckDuplicateNicknameResponseDto checkDuplicateNickname(@RequestParam("nickname") String nickname) {
        boolean available = userService.checkDuplicateNickname(nickname);

        return CheckDuplicateNicknameResponseDto.of(available);
    }
}
