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
    private Long courseId;
    private Long professorId;

    public boolean isExistField() {
        return fieldId != null;
    }

    public boolean isExistSkills() {
        return skillIds != null && !skillIds.isEmpty();
    }

    public boolean isExistTags() {
        return tagIds != null && !tagIds.isEmpty();
    }

    public boolean isExistRoles() {
        return roleIds != null && !roleIds.isEmpty();
    }

    public boolean isExistCourse() {
        return courseId != null;
    }

    public boolean isExistProfessor() {
        return professorId != null;
    }
}
