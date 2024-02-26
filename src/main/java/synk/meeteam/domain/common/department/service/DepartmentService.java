package synk.meeteam.domain.common.department.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.department.repository.DepartmentRepository;
import synk.meeteam.domain.common.university.entity.University;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getDepartmentsByUniversity(University university) {
        List<Department> departments = departmentRepository.findAllByUniversity(university);
        return departments;
    }
}
