package synk.meeteam.domain.common.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import synk.meeteam.domain.common.department.entity.Department;


public record GetDepartmentResponseDto(

        @Schema(description = "학과 id", example = "1")
        Long departmentId,

        @Schema(description = "학과 이름", example = "소프트웨어학부")
        String departmentName
) {
        public static GetDepartmentResponseDto of(Department department) {
                return new GetDepartmentResponseDto(department.getId(), department.getName());
        }
}
