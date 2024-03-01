package synk.meeteam.domain.common.role.repository;

import static synk.meeteam.domain.common.role.exception.RoleExceptionType.INVALID_ROLE_ID;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.exception.RoleException;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long roleId);

    default Role findByIdOrElseThrowException(Long roleId) {
        return findById(roleId).orElseThrow(() -> new RoleException(INVALID_ROLE_ID));
    }
}
