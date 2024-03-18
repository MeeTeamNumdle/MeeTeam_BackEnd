package synk.meeteam.domain.portfolio.portfolio;

import java.util.List;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;

public class PortfolioFixture {
    public static List<Portfolio> createPortfolioFixtures_1_2() {
        return List.of(
                new Portfolio(1L, "타이틀1", "디스크립션1", true, 1),
                new Portfolio(2L, "타이틀2", "디스크립션2", true, 2)
        );
    }

    public static List<Portfolio> createPortfolioFixtures_2_1() {
        return List.of(
                new Portfolio(2L, "타이틀2", "디스크립션2", true, 2),
                new Portfolio(1L, "타이틀1", "디스크립션1", true, 1)
        );
    }
}
