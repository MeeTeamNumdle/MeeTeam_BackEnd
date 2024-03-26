package synk.meeteam.domain.user.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.user.user.dto.request.UpdateProfileRequestDto;
import synk.meeteam.domain.user.user.dto.response.CheckDuplicateNicknameResponseDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.service.ProfileFacade;
import synk.meeteam.domain.user.user.service.UserService;
import synk.meeteam.global.util.Encryption;
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
    public ResponseEntity<GetProfileResponseDto> getProfile(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(profileFacade.readProfile(userId));
    }

    @GetMapping("encrypt/{userId}")
    public ResponseEntity<String> getEncryptedId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(Encryption.encryptLong(userId));
    }


    @Override
    @GetMapping("/search/check-duplicate")
    public ResponseEntity<CheckDuplicateNicknameResponseDto> checkDuplicateNickname(
            @RequestParam("nickname") String nickname) {
        boolean available = userService.checkAvailableNickname(nickname);

        return ResponseEntity.ok(CheckDuplicateNicknameResponseDto.of(available));
    }
}