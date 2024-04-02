package synk.meeteam.domain.portfolio.portfolio.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;
import synk.meeteam.domain.user.user.entity.User;

public interface PortfolioCustomRepository {
    Slice<GetProfilePortfolioDto> findUserPortfoliosByUserOrderByCreatedAtDesc(Pageable pageable, User user);
}
