package synk.meeteam.domain.common.department;

import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.department.repository.DepartmentRepository;
import synk.meeteam.domain.common.department.service.DepartmentService;
import synk.meeteam.domain.common.university.entity.University;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Test
    public void 특정대학학과리스트조회_학과리스트반환_특정대학이주어질때() {
        // given
        University university = new University(1L, "광운대학교", "kw.ac.kr");
        doReturn(DepartmentFixture.createDepartments()).when(departmentRepository).findAllByUniversity(university);

        // when
        List<Department> departments = departmentService.getDepartmentsByUniversity(university);

        // then
        Assertions.assertThat(departments).isNotEmpty();
    }
}
