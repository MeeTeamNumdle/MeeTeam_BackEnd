package synk.meeteam.domain.portfolio.portfolio.repository;

import static synk.meeteam.domain.portfolio.portfolio.exception.PortfolioExceptionType.NOT_FOUND_PORTFOLIO;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.exception.PortfolioException;
import synk.meeteam.global.entity.DeleteStatus;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>, PortfolioCustomRepository {
    List<Portfolio> findAllByCreatedByAndIsPinTrue(Long userId);

    List<Portfolio> findAllByIsPinTrueAndCreatedByOrderByPinOrderAsc(Long userId);

    @Query("SELECT p FROM Portfolio p LEFT JOIN FETCH p.role LEFT JOIN FETCH p.field where p.id=:id and p.deleteStatus=:deleteStatus")
    Optional<Portfolio> findByIdWithFieldAndRoleAndDeleteStatus(@Param("id") Long portfolioId,
                                                                @Param("deleteStatus") DeleteStatus deleteStatus);

    Optional<Portfolio> findByIdAndDeleteStatus(Long portfolioId, DeleteStatus deleteStatus);

    default Portfolio findByIdAndAliveOrElseThrow(Long portfolioId) {
        return findByIdAndDeleteStatus(portfolioId, DeleteStatus.ALIVE).orElseThrow(
                () -> new PortfolioException(NOT_FOUND_PORTFOLIO));
    }

    default Portfolio findByIdAndAliveWithFieldAndRoleOrElseThrow(Long portfolioId) {
        return findByIdWithFieldAndRoleAndDeleteStatus(portfolioId, DeleteStatus.ALIVE).orElseThrow(
                () -> new PortfolioException(NOT_FOUND_PORTFOLIO));
    }

    List<Portfolio> findAllByCreatedBy(Long userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Portfolio p WHERE p.id IN :portfolioIds")
    void deleteAllByIdsInQuery(List<Long> portfolioIds);

}
