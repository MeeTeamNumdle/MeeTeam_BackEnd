package synk.meeteam.domain.role.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.recruitment.dto.CreateRecruitmentRoleAndSpecDto;
import synk.meeteam.domain.role.entity.Role;
import synk.meeteam.domain.role.repository.RoleRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public List<CreateRecruitmentRoleAndSpecDto> createRecruitmentRole(List<CreateRecruitmentRoleAndSpecDto> roleRequestDtos){
        List<CreateRecruitmentRoleAndSpecDto> responses = new ArrayList<>();

        roleRequestDtos.stream().forEach(requiredRole ->{
            Role role = createRole((String) requiredRole.role());
            responses.add(CreateRecruitmentRoleAndSpecDto.ofRole(role, requiredRole.count(), requiredRole.specNames()));
        });

        return responses;
    }

    @Transactional
    public Role createRole(String roleName){
        Role role = roleRepository.findByName(roleName).orElse(null);

        if(role == null){
            Role newRole = Role.builder().name(roleName).build();
            roleRepository.save(newRole);
            return newRole;
        }
        return role;
    }
}
