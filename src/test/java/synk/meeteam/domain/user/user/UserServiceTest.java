package synk.meeteam.domain.user.user;

import static org.mockito.Mockito.doReturn;
import static synk.meeteam.domain.user.user.UserFixtures.NICKNAME;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.department.repository.DepartmentRepository;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.user.repository.UserRepository;
import synk.meeteam.domain.user.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UniversityRepository universityRepository;
    @Mock
    private DepartmentRepository departmentRepository;

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
        org.assertj.core.api.Assertions.assertThat(available)
                .isEqualTo(false);
    }
}
