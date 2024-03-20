package synk.meeteam.domain.user.user;


import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.entity.enums.Authority;
import synk.meeteam.domain.user.user.entity.enums.PlatformType;

public class UserFixtures {
    public static String NICKNAME = "song123";
    public static String NORMAL_NICKNAME = "goder0";


    public static User createUser() {
        University university = new University(1L, "광운대", "kw.ac.kr");
        Department department = new Department(1L, university, "소프트");

        return new User("mikekks", "송민규", NICKNAME, "12", "qwe", 2018, "Qwe", Authority.USER,
                PlatformType.NAVER,
                "Di7lChMGxjZVTai6d76Ho1YLDU_xL8tl1CfdPMV5SQM", university, department);
    }
}
