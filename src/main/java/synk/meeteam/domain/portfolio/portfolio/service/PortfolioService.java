package synk.meeteam.domain.portfolio.portfolio.service;

import static synk.meeteam.domain.portfolio.portfolio.exception.PortfolioExceptionType.NOT_FOUND_PORTFOLIO;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.exception.PortfolioException;
import synk.meeteam.domain.portfolio.portfolio.repository.PortfolioRepository;
import synk.meeteam.domain.user.user.entity.User;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public List<Portfolio> changePinPortfoliosByIds(User user, List<Long> portfolioIds) {
        //포트폴리오 핀 설정
        List<Portfolio> oldPinPortfolios = portfolioRepository.findAllByIsPinTrueAndUserOrderByPinOrderAsc(user);
        //기존 핀 해제
        oldPinPortfolios.forEach(Portfolio::unpin);
        //포트폴리오 조회
        List<Portfolio> newPinPortfolios = portfolioRepository.findAllByIdInAndUser(portfolioIds, user);

        //조회한 결과와 요청한 id의 갯수가 상이할 경우
        if (newPinPortfolios.size() != portfolioIds.size()) {
            throw new PortfolioException(NOT_FOUND_PORTFOLIO);
        }
        //핀 설정
        for (int index = 0; index < newPinPortfolios.size(); index++) {
            newPinPortfolios.get(index).putPin(index + 1);
        }

        return newPinPortfolios;
    }
}
