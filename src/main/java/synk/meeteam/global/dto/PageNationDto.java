package synk.meeteam.global.dto;

import java.util.List;

public record PageNationDto<T>(
        List<T> data,
        PageInfo pageInfo
) {
}
