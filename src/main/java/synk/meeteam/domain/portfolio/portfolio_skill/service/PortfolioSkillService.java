package synk.meeteam.domain.portfolio.portfolio_skill.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.skill.exception.SkillException;
import synk.meeteam.domain.common.skill.exception.SkillExceptionType;
import synk.meeteam.domain.common.skill.repository.SkillRepository;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio_skill.entity.PortfolioSkill;
import synk.meeteam.domain.portfolio.portfolio_skill.repository.PortfolioSkillRepository;

@Service
@RequiredArgsConstructor
public class PortfolioSkillService {
    private final PortfolioSkillRepository portfolioSkillRepository;
    private final SkillRepository skillRepository;

    @Transactional
    public void createPortfolioSkill(Portfolio portfolio, List<Long> skillIds) {
        List<Skill> skills = skillRepository.findAllById(skillIds);
        if (skills.size() != skillIds.size()) {
            throw new SkillException(SkillExceptionType.INVALID_SKILL_ID);
        }

        List<PortfolioSkill> portfolioSkills = skills.stream().map(skill -> new PortfolioSkill(portfolio, skill))
                .toList();
        portfolioSkillRepository.saveAll(portfolioSkills);
    }

    public List<PortfolioSkill> getPortfolioSkill(Portfolio portfolio) {
        return portfolioSkillRepository.findAllByPortfolio(portfolio);
    }

//    void editPortfolioSkill()
}
