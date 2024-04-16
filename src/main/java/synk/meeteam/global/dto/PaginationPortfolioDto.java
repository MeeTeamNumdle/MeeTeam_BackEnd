package synk.meeteam.global.dto;

import java.util.List;

public record PaginationPortfolioDto<T>(
        List<T> portfolios,
        PageInfo pageInfo
) {
}
