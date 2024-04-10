package synk.meeteam.domain.common.course.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchCourseDto {
    private Long id;
    private String name;

    @QueryProjection
    public SearchCourseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
