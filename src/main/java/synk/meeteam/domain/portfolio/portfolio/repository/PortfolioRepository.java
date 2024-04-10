package synk.meeteam.domain.portfolio.portfolio.repository;

import static synk.meeteam.domain.portfolio.portfolio.exception.PortfolioExceptionType.NOT_FOUND_PORTFOLIO;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.exception.PortfolioException;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>, PortfolioCustomRepository {
    List<Portfolio> findAllByCreatedByAndIsPinTrue(Long userId);

    List<Portfolio> findAllByIsPinTrueAndCreatedByOrderByProceedStartAsc(Long id);

    @Query("SELECT p FROM Portfolio p LEFT JOIN FETCH p.role LEFT JOIN FETCH p.field where p.id=:id")
    Optional<Portfolio> findByIdWithFieldAndRole(@Param("id") Long portfolioId);

    default Portfolio findByIdOrElseThrow(Long portfolioId) {
        return findById(portfolioId).orElseThrow(() -> new PortfolioException(NOT_FOUND_PORTFOLIO));
    }

    default Portfolio findByIdWithFieldAndRoleOrElseThrow(Long portfolioId) {
        return findByIdWithFieldAndRole(portfolioId).orElseThrow(() -> new PortfolioException(NOT_FOUND_PORTFOLIO));
    }

}
