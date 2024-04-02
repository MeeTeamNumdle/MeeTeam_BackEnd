package synk.meeteam.domain.portfolio.portfolio.dto.response;

import java.util.List;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;
import synk.meeteam.global.dto.PageInfo;

public record GetUserPortfolioResponseDto(
        List<GetProfilePortfolioDto> portfolios,
        PageInfo pageInfo
) {

}
