package synk.meeteam.global.dto;

import java.util.List;

public record PaginationDto<T>(
        List<T> data,
        PageInfo pageInfo
) {
}
