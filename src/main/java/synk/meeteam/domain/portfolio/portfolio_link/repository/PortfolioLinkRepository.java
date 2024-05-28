package synk.meeteam.domain.portfolio.portfolio_link.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio_link.entity.PortfolioLink;

public interface PortfolioLinkRepository extends JpaRepository<PortfolioLink, Long> {
    List<PortfolioLink> findAllByPortfolio(Portfolio portfolio);

    void deleteAllByPortfolio(Portfolio portfolio);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM PortfolioLink p WHERE p.portfolio.id IN :portfolioIds")
    void deleteAllByPortfolioIdsInQuery(List<Long> portfolioIds);
}
