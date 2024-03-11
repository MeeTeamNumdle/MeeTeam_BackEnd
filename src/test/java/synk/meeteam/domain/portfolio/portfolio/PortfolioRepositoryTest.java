package synk.meeteam.domain.portfolio.portfolio;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.repository.PortfolioRepository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
public class PortfolioRepositoryTest {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void 특정유저의핀포트폴리오목록조회_특정유저의핀포트폴리오목록반환() {
        //given
        User user = userRepository.findById(1L).get();
        //when
        List<Portfolio> portfolios = portfolioRepository.findAllByIsPinTrueAndUserOrderByPinOrderAsc(user);
        //then
        assertThat(portfolios).extracting("title").containsExactly("타이틀1", "타이틀2");
    }
}
