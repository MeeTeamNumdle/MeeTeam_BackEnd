package synk.meeteam.domain.portfolio.portfolio.service;

import static synk.meeteam.domain.portfolio.portfolio.exception.PortfolioExceptionType.NOT_YOUR_PORTFOLIO;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.portfolio.portfolio.dto.command.PortfolioCommandMapper;
import synk.meeteam.domain.portfolio.portfolio.dto.request.CreatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.request.UpdatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.response.GetPortfolioResponseDto;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.entity.PortfolioMapper;
import synk.meeteam.domain.portfolio.portfolio.exception.PortfolioException;
import synk.meeteam.domain.portfolio.portfolio_link.dto.PortfolioLinkDto;
import synk.meeteam.domain.portfolio.portfolio_link.entity.PortfolioLink;
import synk.meeteam.domain.portfolio.portfolio_link.service.PortfolioLinkService;
import synk.meeteam.domain.portfolio.portfolio_skill.service.PortfolioSkillService;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.util.Encryption;
import synk.meeteam.infra.s3.S3FileName;
import synk.meeteam.infra.s3.service.S3Service;

@Service
@RequiredArgsConstructor
public class PortfolioFacade {
    private final PortfolioService portfolioService;
    private final PortfolioSkillService portfolioSkillService;
    private final PortfolioLinkService portfolioLinkService;
    private final S3Service s3Service;

    private final PortfolioCommandMapper commandMapper;
    private final PortfolioMapper portfolioMapper;

    @Transactional
    public Long postPortfolio(CreatePortfolioRequestDto requestDto) {
        Portfolio portfolio = portfolioService.postPortfolio(commandMapper.toCreatePortfolioCommand(requestDto));
        portfolioSkillService.createPortfolioSkill(portfolio, requestDto.skills());
        portfolioLinkService.createPortfolioLink(portfolio, requestDto.links());

        return portfolio.getId();
    }

    @Transactional(readOnly = true)
    public GetPortfolioResponseDto getPortfolio(Long portfolioId, User user) {
        Portfolio portfolio = portfolioService.getPortfolio(portfolioId);
        Long userId = user != null ? user.getId() : null;
        if (!portfolio.isAllViewAble(userId)) {
            throw new PortfolioException(NOT_YOUR_PORTFOLIO);
        }
        String writerEncryptedId = Encryption.encryptLong(portfolio.getCreatedBy());

        List<Skill> skills = portfolioSkillService.getPortfolioSkill(portfolio);
        List<PortfolioLink> links = portfolioLinkService.getPortfolioLink(portfolio);
        String zipFileUrl = s3Service.createPreSignedGetUrl(
                S3FileName.getPortfolioPath(writerEncryptedId),
                portfolio.getZipFileName());
        List<Portfolio> otherPinPortfolios = getUserPortfolio(portfolio);
        return new GetPortfolioResponseDto(
                portfolio.getTitle(),
                portfolio.getDescription(),
                portfolio.getContent(),
                zipFileUrl,
                portfolio.getFileOrder(),
                portfolio.getField().getName(),
                portfolio.getRole().getName(),
                portfolio.getProceedStart(),
                portfolio.getProceedEnd(),
                portfolio.getProceedType().getName(),
                skills.stream().map(skill -> new SkillDto(skill.getId(), skill.getName())).toList(),
                links.stream().map(link -> new PortfolioLinkDto(link.getUrl(), link.getDescription())).toList(),
                otherPinPortfolios.stream().map(otherPortfolio ->
                        portfolioMapper.toGetProfilePortfolioDto(otherPortfolio,
                                s3Service.createPreSignedGetUrl(S3FileName.getPortfolioPath(writerEncryptedId),
                                        otherPortfolio.getMainImageFileName()))).toList(),
                portfolio.isWriter(userId)
        );
    }

    @Transactional
    public Long editPortfolio(Long portfolioId, User user, UpdatePortfolioRequestDto requestDto) {
        Portfolio portfolio = portfolioService.getPortfolio(portfolioId);
        if (!portfolio.isWriter(user.getId())) {
            throw new PortfolioException(NOT_YOUR_PORTFOLIO);
        }
        portfolioService.editPortfolio(portfolio, user, commandMapper.toUpdatePortfolioCommand(requestDto));
        portfolioSkillService.editPortfolioSkill(portfolio, requestDto.skills());
        portfolioLinkService.editPortfolioLink(portfolio, requestDto.links());

        return portfolioId;
    }

    private List<Portfolio> getUserPortfolio(Portfolio portfolio) {
        List<Portfolio> userPortfolios = portfolioService.getMyPinPortfolio(portfolio.getCreatedBy());
        userPortfolios.remove(portfolio);
        return userPortfolios;
    }
}
