package synk.meeteam.domain.portfolio.portfolio_skill.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio_skill.entity.PortfolioSkill;

public interface PortfolioSkillRepository extends JpaRepository<PortfolioSkill, Long> {
    @Query("SELECT ps FROM PortfolioSkill ps JOIN FETCH ps.skill WHERE ps.portfolio=:portfolio")
    List<PortfolioSkill> findAllByPortfolioWithSkill(@Param("portfolio") Portfolio portfolio);

    void deleteAllByPortfolio(Portfolio portfolio);

    @Modifying
    @Transactional
    @Query("DELETE FROM PortfolioSkill p WHERE p.portfolio.id IN :portfolioIds")
    void deleteAllByPortfolioIdsInQuery(List<Long> portfolioIds);
}
