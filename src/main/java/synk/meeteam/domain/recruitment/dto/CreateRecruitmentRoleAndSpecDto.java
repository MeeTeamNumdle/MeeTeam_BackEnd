package synk.meeteam.domain.recruitment.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import synk.meeteam.domain.role.entity.Role;
import synk.meeteam.domain.spec.entity.Spec;

public record CreateRecruitmentRoleAndSpecDto<T, E>(
        @NotNull T role,
        int count,
        @NotNull List<E> specNames
) {
    public static CreateRecruitmentRoleAndSpecDto ofRole(Role role, int count, List<String> specNames){
        return new CreateRecruitmentRoleAndSpecDto(role, count, specNames);
    }

    public static CreateRecruitmentRoleAndSpecDto ofSpecs(Role role, int count, List<Spec> specNames){
        return new CreateRecruitmentRoleAndSpecDto(role, count, specNames);
    }
}
