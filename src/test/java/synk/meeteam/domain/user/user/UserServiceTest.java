package synk.meeteam.domain.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static synk.meeteam.domain.user.user.UserFixture.NICKNAME;
import static synk.meeteam.domain.user.user.exception.UserExceptionType.NOT_FOUND_USER;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.user.exception.UserException;
import synk.meeteam.domain.user.user.repository.UserRepository;
import synk.meeteam.domain.user.user.service.UserServiceImpl;
import synk.meeteam.global.util.Encryption;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void 실패_닉네임이미존재함() {
        // given
        University university = new University(1L, "광운대", "kw.ac.kr");
        Department department = new Department(1L, university, "소프트");
        User prevSavedUser = new User("mikekks", "송민규", NICKNAME, "12", "qwe", 2018, "Qwe", Authority.USER,
                PlatformType.NAVER,
                "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM", university, department);

        doReturn(Optional.of(prevSavedUser)).when(userRepository).findByNickname(NICKNAME);

        userRepository.saveAndFlush(prevSavedUser);

        // when
        boolean available = userService.checkAvailableNickname(NICKNAME);

        // then
        assertThat(available).isEqualTo(false);
    }

    @Test
    void 암호화된유저아이디로유저조회_조회성공() {
        try (MockedStatic<Encryption> utilities = Mockito.mockStatic(Encryption.class)) {
            //given
            String encryptedId = "QLALFQJSGH";
            utilities.when(() -> Encryption.decryptLong(encryptedId)).thenReturn(1L);
            doReturn(User.builder().nickname("테스트").build()).when(userRepository).findByIdFetchRole(any());

            //when
            User user = userService.findByEncryptedId(encryptedId);

            //then
            assertThat(user.getNickname()).isEqualTo("테스트");
        }
    }

    @Test
    void 암호화된유저아이디로유저조회_조회실패_잘못된암호화유저아이디() {
        try (MockedStatic<Encryption> utilities = Mockito.mockStatic(Encryption.class)) {
            //given
            String encryptedId = "QLALFQJSGH";
            utilities.when(() -> Encryption.decryptLong(encryptedId)).thenReturn(null);

            //when then
            Assertions.assertThatThrownBy(() -> userService.findByEncryptedId(encryptedId))
                    .isExactlyInstanceOf(UserException.class)
                    .hasMessage(NOT_FOUND_USER.message());
        }
    }

    @Test
    void 암호화된유저아이디로유저조회_조회실패_존재하지않는유저아이디() {
        try (MockedStatic<Encryption> utilities = Mockito.mockStatic(Encryption.class)) {
            //given
            String encryptedId = "QLALFQJSGH";
            utilities.when(() -> Encryption.decryptLong(encryptedId)).thenReturn(0L);
            when(userRepository.findByIdFetchRole(any())).thenThrow(
                    new UserException(NOT_FOUND_USER));
            //when then
            Assertions.assertThatThrownBy(
                            () -> userService.findByEncryptedId(encryptedId)
                    )
                    .isExactlyInstanceOf(UserException.class)
                    .hasMessage(NOT_FOUND_USER.message());
        }
    }
}
