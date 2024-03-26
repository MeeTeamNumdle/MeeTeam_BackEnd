package synk.meeteam.domain.portfolio.portfolio;

import java.time.LocalDate;
import java.util.List;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.role.entity.Role;
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

    public static Portfolio createUserPortfolio(String title, boolean isPin, int pinOrder, Long createdBy,
                                                LocalDate start, Role role,
                                                Field field) {
        Portfolio portfolio = Portfolio.builder()
                .title(title)
                .content("컨텐츠")
                .description("디스크립션")
                .proceedStart(start)
                .proceedEnd(LocalDate.now())
                .isPin(isPin)
                .pinOrder(pinOrder)
                .role(role)
                .field(field)
                .build();
        portfolio.setCreatedBy(createdBy);
        return portfolio;
    }
}
