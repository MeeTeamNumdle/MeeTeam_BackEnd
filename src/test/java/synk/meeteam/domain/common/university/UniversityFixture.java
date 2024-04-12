package synk.meeteam.domain.common.university;

import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.common.university.entity.University;

public class UniversityFixture {

    public static List<University> createUniversities() {
        List<University> universities = new ArrayList<>();
        universities.add(new University(1L, "광운대학교", "kw.ac.kr"));
        universities.add(new University(2L, "서울대학교", "snu.ac.kr"));
        universities.add(new University(3L, "연세대학교", "ys.ac.kr"));
        universities.add(new University(4L, "성균관대학교", "shu.ac.kr"));
        universities.add(new University(5L, "한국외국대학교", "huf.ac.kr"));
        return universities;
    }
}
