package synk.meeteam.domain.portfolio.portfolio_link.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio_link.dto.PortfolioLinkDto;
import synk.meeteam.domain.portfolio.portfolio_link.entity.PortfolioLink;
import synk.meeteam.domain.portfolio.portfolio_link.repository.PortfolioLinkRepository;

@Service
@RequiredArgsConstructor
public class PortfolioLinkService {
    private final PortfolioLinkRepository portfolioLinkRepository;

    @Transactional
    public void createPortfolioLink(Portfolio portfolio, List<PortfolioLinkDto> portfolioLinkDtos) {
        List<PortfolioLink> portfolioLinks = portfolioLinkDtos.stream()
                .map(portfolioLinkDto -> new PortfolioLink(portfolio, portfolioLinkDto.url(),
                        portfolioLinkDto.description())).toList();
        portfolioLinkRepository.saveAll(portfolioLinks);
    }

    public List<PortfolioLink> getPortfolioLink(Portfolio portfolio) {
        return portfolioLinkRepository.findAllByPortfolio(portfolio);
    }
}
