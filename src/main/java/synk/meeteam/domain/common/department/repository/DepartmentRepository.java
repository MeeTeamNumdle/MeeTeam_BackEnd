package synk.meeteam.domain.common.department.repository;

import static synk.meeteam.domain.common.department.exception.DepartmentExceptionType.NOT_FOUND_DEPARTMENT_ID;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.department.entity.Department;
import synk.meeteam.domain.common.department.exception.DepartmentException;
import synk.meeteam.domain.common.university.entity.University;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findById(Long departmentId);

    default Department findByIdOrElseThrowException(Long departmentId) {
        return findById(departmentId).orElseThrow(() -> new DepartmentException(
                NOT_FOUND_DEPARTMENT_ID));
    }

    List<Department> findAllByUniversity(University university);

}
