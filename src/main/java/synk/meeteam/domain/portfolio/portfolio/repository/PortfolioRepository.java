package synk.meeteam.domain.portfolio.portfolio.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.user.user.entity.User;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByIsPinTrueAndUserOrderByPinOrderAsc(User user);
}
