package synk.meeteam.domain.common.tag.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchTagDto {
    private Long id;
    private String name;

    @QueryProjection
    public SearchTagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
