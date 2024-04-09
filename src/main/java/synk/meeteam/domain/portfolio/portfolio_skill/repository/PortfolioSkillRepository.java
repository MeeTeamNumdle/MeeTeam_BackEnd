package synk.meeteam.domain.portfolio.portfolio_skill.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio_skill.entity.PortfolioSkill;

public interface PortfolioSkillRepository extends JpaRepository<PortfolioSkill, Long> {
    List<PortfolioSkill> findAllByPortfolio(Portfolio portfolio);

    void deleteAllByPortfolio(Portfolio portfolio);
}
