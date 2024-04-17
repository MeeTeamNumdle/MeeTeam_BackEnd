package synk.meeteam.domain.portfolio.portfolio.service;

import java.util.List;
import synk.meeteam.domain.portfolio.portfolio.dto.SimplePortfolioDto;
import synk.meeteam.domain.portfolio.portfolio.dto.command.CreatePortfolioCommand;
import synk.meeteam.domain.portfolio.portfolio.dto.command.UpdatePortfolioCommand;
import synk.meeteam.domain.portfolio.portfolio.dto.response.GetUserPortfolioResponseDto;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.dto.PaginationPortfolioDto;

public interface PortfolioService {
    List<Portfolio> changePinPortfoliosByIds(Long userId, List<Long> portfolioIds);

    List<Portfolio> getMyPinPortfolio(Long userId);

    GetUserPortfolioResponseDto getSliceMyAllPortfolio(int page, int size, User user);

    PaginationPortfolioDto<SimplePortfolioDto> getPageMyAllPortfolio(int page, int size, User user);

    Portfolio postPortfolio(CreatePortfolioCommand command);

    Portfolio getPortfolio(Long portfolioId);

    Portfolio getPortfolio(Long portfolioId, User user);

    Portfolio editPortfolio(Portfolio portfolio, User user, UpdatePortfolioCommand command);

    void deletePortfolio(Long portfolioId, User user);

}
