package synk.meeteam.domain.portfolio.portfolio.service;

import java.util.List;
import synk.meeteam.domain.portfolio.portfolio.dto.command.CreatePortfolioCommand;
import synk.meeteam.domain.portfolio.portfolio.dto.command.UpdatePortfolioCommand;
import synk.meeteam.domain.portfolio.portfolio.dto.response.GetUserPortfolioResponseDto;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.user.user.entity.User;

public interface PortfolioService {
    List<Portfolio> changePinPortfoliosByIds(Long userId, List<Long> portfolioIds);

    List<Portfolio> getMyPinPortfolio(Long userId);

    GetUserPortfolioResponseDto getMyAllPortfolio(int page, int size, User user);

    Portfolio postPortfolio(CreatePortfolioCommand command);

    Portfolio getPortfolio(Long portfolioId);
  
    Portfolio getPortfolio(Long portfolioId, User user);

    Portfolio editPortfolio(Portfolio portfolio, User user, UpdatePortfolioCommand command);

}
