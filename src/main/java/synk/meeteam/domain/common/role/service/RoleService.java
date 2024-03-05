package synk.meeteam.domain.common.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    // 트랜잭션으로 부터 자유로운 로직이기에 트랜잭션 어노테이션 제외
    public Role findById(Long roleId) {
        return roleRepository.findByIdOrElseThrowException(roleId);
    }
}
