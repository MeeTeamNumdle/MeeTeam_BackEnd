package synk.meeteam.domain.portfolio.portfolio.repository;

import static synk.meeteam.domain.portfolio.portfolio.exception.PortfolioExceptionType.NOT_FOUND_PORTFOLIO;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.exception.PortfolioException;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>, PortfolioCustomRepository {
    List<Portfolio> findAllByCreatedByAndIsPinTrue(Long userId);

    List<Portfolio> findAllByIsPinTrueAndCreatedByOrderByProceedStartAsc(Long id);

    default Portfolio findByIdOrElseThrow(Long portfolioId) {
        return findById(portfolioId).orElseThrow(() -> new PortfolioException(NOT_FOUND_PORTFOLIO));
    }
}
