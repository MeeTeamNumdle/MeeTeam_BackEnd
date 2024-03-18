package synk.meeteam.domain.portfolio.portfolio.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByIsPinTrueAndCreatedByOrderByPinOrderAsc(Long id);

    List<Portfolio> findAllByIdInAndCreatedBy(List<Long> ids, Long id);
}
