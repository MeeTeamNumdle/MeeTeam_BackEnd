package synk.meeteam.domain.common.department;

import java.util.ArrayList;
import java.util.List;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.university.entity.University;

public class DepartmentFixture {

    public static List<Department> createDepartments() {
        University university = new University(1L, "광운대학교", "kw.ac.kr");
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1L, university, "소프트웨어학부"));
        departments.add(new Department(2L, university, "컴공"));
        departments.add(new Department(3L, university, "정융"));
        departments.add(new Department(4L, university, "전자공학과"));

        return departments;
    }
}
