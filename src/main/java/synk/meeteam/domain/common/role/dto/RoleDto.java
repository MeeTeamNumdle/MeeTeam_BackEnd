package synk.meeteam.domain.common.role.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String name;

    @QueryProjection
    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
