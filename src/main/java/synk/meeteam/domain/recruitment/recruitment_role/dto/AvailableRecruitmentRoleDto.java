package synk.meeteam.domain.recruitment.recruitment_role.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class AvailableRecruitmentRoleDto {
    // recruitment_role_id
    private Long id;

    // role_name
    private String name;

    @QueryProjection
    public AvailableRecruitmentRoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
