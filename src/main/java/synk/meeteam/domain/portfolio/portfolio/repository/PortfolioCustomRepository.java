package synk.meeteam.domain.portfolio.portfolio.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.user.user.entity.User;

public interface PortfolioCustomRepository {
    Slice<GetProfilePortfolioDto> findUserPortfoliosByUserOrderByCreatedAtDesc(Pageable pageable, User user);

    List<Portfolio> findAllByCreatedByAndIsPinTrueOrderByIds(Long userId, List<Long> portfolioIds);
}
