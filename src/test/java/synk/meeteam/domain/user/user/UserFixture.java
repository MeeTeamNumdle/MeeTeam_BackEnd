package synk.meeteam.domain.user.user;


import java.time.LocalDate;
import java.util.List;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.user.award.dto.UpdateAwardDto;
import synk.meeteam.domain.user.user.dto.request.UpdateProfileRequestDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;
import synk.meeteam.domain.user.user_link.dto.UpdateUserLinkDto;

public class UserFixture {
    public static String NICKNAME = "song123";
    public static String NORMAL_NICKNAME = "goder0";

    public static User createUserFixture() {
        return User.builder()
                .universityEmail("test@kw.ac.kr")
                .name("test")
                .build();
    }

    public static User createUser() {
        University university = new University(1L, "광운대", "kw.ac.kr");
        Department department = new Department(1L, university, "소프트");

        return new User("mikekks", "송민규", NICKNAME, "12", "qwe", 2018, "Qwe", Authority.USER,
                PlatformType.NAVER,
                "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM", university, department);
    }

    public static UpdateProfileRequestDto createEditProfileDto() {
        return new UpdateProfileRequestDto(
                "부겸",
                "goder",
                "imageUrl",
                "010-1234-5678",
                true,
                true,
                "aaa@naver.com",
                true,
                true,
                "나는 이런사람이야.",
                "나는이런사람이야나는이런사람이야나는이런사람이야",
                1L,
                4.3,
                4.5,
                List.of(1L, 2L, 3L),
                List.of(new UpdateUserLinkDto("naver.com", "네이버")),
                List.of(new UpdateAwardDto("공공데이터공모전", "장려상수상", LocalDate.parse("2023-02-02"),
                        LocalDate.parse("2023-03-01"))),
                List.of(1L, 2L)
        );
    }

}
