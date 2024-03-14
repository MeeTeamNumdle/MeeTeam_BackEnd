package synk.meeteam.domain.portfolio.portfolio.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.repository.PortfolioRepository;
import synk.meeteam.domain.user.user.entity.User;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    public void updatePinPortfoliosByIds(User user, List<Long> portfolioIds) {
        //포트폴리오 핀 설정
        List<Portfolio> oldPinPortfolios = portfolioRepository.findAllByIsPinTrueAndUserOrderByPinOrderAsc(user);
        //기존 핀 해제
        oldPinPortfolios.forEach(Portfolio::unpin);
        //포트폴리오 조회
        List<Portfolio> newPinPortfolios = portfolioRepository.findAllById(portfolioIds);
        //핀 설정
        for (int index = 0; index < newPinPortfolios.size(); index++) {
            newPinPortfolios.get(index).putPin(index + 1);
        }
    }
}
