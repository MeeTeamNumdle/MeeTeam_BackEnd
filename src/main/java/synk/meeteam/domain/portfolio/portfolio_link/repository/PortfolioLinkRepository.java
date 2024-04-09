package synk.meeteam.domain.portfolio.portfolio_link.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio_link.entity.PortfolioLink;

public interface PortfolioLinkRepository extends JpaRepository<PortfolioLink, Long> {
    List<PortfolioLink> findAllByPortfolio(Portfolio portfolio);
}
