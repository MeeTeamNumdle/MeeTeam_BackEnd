package synk.meeteam.domain.common.department;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.department.repository.DepartmentRepository;
import synk.meeteam.domain.common.university.entity.University;
import synk.meeteam.domain.common.university.repository.UniversityRepository;

@DataJpaTest
public class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Test
    public void 특정대학학과리스트조회_학과리스트반환_특정대학이주어질때() {
        // given
        University university = new University(1L, "광운대학교", "kw.ac.kr");
        universityRepository.saveAndFlush(university);
        Department department = new Department(1L, university, "소프트웨어학부");
        departmentRepository.saveAndFlush(department);

        // when
        List<Department> departments = departmentRepository.findAllByUniversity(university);

        // then
        Assertions.assertThat(departments.size()).isNotEqualTo(0);
    }

    @Test
    public void 특정대학학과리스트조회_예외발생_유효하지않은대학이주어질때() {
        // given
        University university = new University(100L, "광운대학교", "kw.ac.kr");

        // when
        List<Department> departments = departmentRepository.findAllByUniversity(university);

        // then
        Assertions.assertThat(departments).isEmpty();
    }
}
