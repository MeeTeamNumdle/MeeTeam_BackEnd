package synk.meeteam.domain.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static synk.meeteam.domain.user.user.UserFixtures.NICKNAME;
import static synk.meeteam.domain.user.user.UserFixtures.NORMAL_NICKNAME;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.department.repository.DepartmentRepository;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.user.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("중복된 닉네임이 존재하는지 조회하는 기능")
    public void 성공_중복된닉네임존재() {
        // given
        University university = new University(1L, "광운대", "kw.ac.kr");
        University university1 = universityRepository.saveAndFlush(university);
        Department department = new Department(1L, university1, "소프트");
        Department department1 = departmentRepository.saveAndFlush(department);
        User prevSavedUser = new User("mikekks", "송민규", NICKNAME, "12", "qwe", 2018, "Qwe", Authority.USER,
                PlatformType.NAVER,
                "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM", university1, department1);
        userRepository.saveAndFlush(prevSavedUser);

        // when
        User duplicatedUser = userRepository.findByNickname(NICKNAME).orElse(null);

        // then
        assertThat(duplicatedUser.getNickname()).isEqualTo(NICKNAME);
    }

    @Test
    @DisplayName("중복된 닉네임이 존재하지 않는 경우 null를 반환하는 기능")
    public void 성공_중복된닉네임존재하지않음() {
        // given
        University university = new University(1L, "광운대", "kw.ac.kr");
        University university1 = universityRepository.saveAndFlush(university);
        Department department = new Department(1L, university1, "소프트");
        Department department1 = departmentRepository.saveAndFlush(department);
        User prevSavedUser = new User("mikekks", "송민규", NICKNAME, "12", "qwe", 2018, "Qwe", Authority.USER,
                PlatformType.NAVER,
                "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM", university1, department1);
        userRepository.saveAndFlush(prevSavedUser);

        // when
        User duplicatedUser = userRepository.findByNickname(NORMAL_NICKNAME).orElse(null);

        // then
        assertThat(duplicatedUser).isNull();
    }
}
