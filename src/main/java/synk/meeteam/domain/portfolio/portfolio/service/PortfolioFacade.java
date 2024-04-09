package synk.meeteam.domain.portfolio.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.portfolio.portfolio.dto.command.PortfolioCommandMapper;
import synk.meeteam.domain.portfolio.portfolio.dto.request.CreatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio_link.service.PortfolioLinkService;
import synk.meeteam.domain.portfolio.portfolio_skill.service.PortfolioSkillService;

@Service
@RequiredArgsConstructor
public class PortfolioFacade {
    private final PortfolioService portfolioService;
    private final PortfolioSkillService portfolioSkillService;
    private final PortfolioLinkService portfolioLinkService;

    private final PortfolioCommandMapper commandMapper;

    @Transactional
    public Long postPortfolio(CreatePortfolioRequestDto requestDto) {
        Portfolio portfolio = portfolioService.postPortfolio(commandMapper.toCreatePortfolioCommand(requestDto));
        portfolioSkillService.createPortfolioSkill(portfolio, requestDto.skills());
        portfolioLinkService.createPortfolioLink(portfolio, requestDto.links());

        return portfolio.getId();
    }
}
