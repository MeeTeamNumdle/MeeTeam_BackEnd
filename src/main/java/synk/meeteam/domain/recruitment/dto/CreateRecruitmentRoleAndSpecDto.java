package synk.meeteam.domain.recruitment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import synk.meeteam.domain.role.entity.Role;
import synk.meeteam.domain.spec.entity.Spec;

public record CreateRecruitmentRoleAndSpecDto<T, E>(
        @NotNull
        @Schema(description = "필요한 역할 ", example = "백엔드개발자")
        T role,
        @Schema(description = "필요한 사람 수 ", example = "2")
        int count,
        @NotNull
        @Schema(description = "필요한 스펙들", example = "[spring, java, JPA]")
        List<E> specNames
) {
    public static CreateRecruitmentRoleAndSpecDto ofRole(Role role, int count, List<String> specNames){
        return new CreateRecruitmentRoleAndSpecDto(role, count, specNames);
    }

    public static CreateRecruitmentRoleAndSpecDto ofSpecs(Role role, int count, List<Spec> specNames){
        return new CreateRecruitmentRoleAndSpecDto(role, count, specNames);
    }
}
