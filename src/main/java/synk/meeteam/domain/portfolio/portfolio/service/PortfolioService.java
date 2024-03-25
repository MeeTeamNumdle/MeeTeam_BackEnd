package synk.meeteam.domain.portfolio.portfolio.service;

import java.util.List;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;

public interface PortfolioService {
    List<Portfolio> changePinPortfoliosByIds(Long id, List<Long> portfolioIds);
}
