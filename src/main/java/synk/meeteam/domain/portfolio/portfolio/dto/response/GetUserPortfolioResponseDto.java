package synk.meeteam.domain.portfolio.portfolio.dto.response;

import java.util.List;
import synk.meeteam.domain.portfolio.portfolio.dto.SimplePortfolioDto;
import synk.meeteam.global.dto.SliceInfo;

public record GetUserPortfolioResponseDto(
        List<SimplePortfolioDto> portfolios,
        SliceInfo pageInfo
) {

}
