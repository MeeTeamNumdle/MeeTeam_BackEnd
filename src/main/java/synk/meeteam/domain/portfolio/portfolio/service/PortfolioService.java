package synk.meeteam.domain.portfolio.portfolio.service;

import java.util.List;
import synk.meeteam.domain.portfolio.portfolio.dto.response.GetUserPortfolioResponseDto;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.user.user.entity.User;

public interface PortfolioService {
    List<Portfolio> changePinPortfoliosByIds(Long userId, List<Long> portfolioIds);

    List<Portfolio> getUserPinPortfolio(Long userId);

    GetUserPortfolioResponseDto getMyAllPortfolio(int page, int size, User user);
}
