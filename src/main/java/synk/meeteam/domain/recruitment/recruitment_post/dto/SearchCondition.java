package synk.meeteam.domain.recruitment.recruitment_post.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.Scope;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCondition {
    private Long fieldId;
    private Scope scope;
    private Category category;
    private List<Long> skillIds;
    private List<Long> tagIds;
    private List<Long> roleIds;
}
