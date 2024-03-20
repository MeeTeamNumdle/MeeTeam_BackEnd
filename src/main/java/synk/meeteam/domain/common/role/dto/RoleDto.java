package synk.meeteam.domain.common.role.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDto {
    @Schema(description = "역할 id", example = "1")
    private Long id;

    @Schema(description = "역할 이름", example = "백엔드개발자")
    private String name;

    @QueryProjection
    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
