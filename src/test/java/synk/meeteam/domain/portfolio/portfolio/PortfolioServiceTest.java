package synk.meeteam.domain.portfolio.portfolio;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static synk.meeteam.domain.portfolio.portfolio.exception.PortfolioExceptionType.NOT_FOUND_PORTFOLIO;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.exception.PortfolioException;
import synk.meeteam.domain.portfolio.portfolio.repository.PortfolioRepository;
import synk.meeteam.domain.portfolio.portfolio.service.PortfolioService;
import synk.meeteam.domain.portfolio.portfolio.service.PortfolioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PortfolioServiceTest {
    @Mock
    private PortfolioService portfolioService;

    @InjectMocks
    private PortfolioServiceImpl portfolioServiceImpl;

    @Mock
    private PortfolioRepository portfolioRepository;

    @BeforeEach
    void setup() {
        portfolioService = portfolioServiceImpl;
    }

    @Test
    void 포트폴리오핀설정_핀설정성공_정상핀아이디목록() {
        //given
        doReturn(PortfolioFixture.createPortfolioFixtures_1_2()).when(portfolioRepository)
                .findAllByIsPinTrueAndCreatedByOrderByPinOrderAsc(anyLong());
        List<Long> pins = List.of(2L, 1L);
        doReturn(PortfolioFixture.createPortfolioFixtures_2_1()).when(portfolioRepository)
                .findAllByIdInAndCreatedBy(eq(pins), anyLong());
        //when
        List<Portfolio> portfolios = portfolioService.changePinPortfoliosByIds(1L, pins);
        //then
        Assertions.assertThat(portfolios).extracting("isPin").containsExactly(true, true);
        Assertions.assertThat(portfolios).extracting("pinOrder").containsExactly(1, 2);
        Assertions.assertThat(portfolios).extracting("title").containsExactly("타이틀2", "타이틀1");
    }

    @Test
    void 포트폴리오핀설정_핀설정실패_조회되지않는아이디() {
        //given
        doReturn(PortfolioFixture.createPortfolioFixtures_1_2()).when(portfolioRepository)
                .findAllByIsPinTrueAndCreatedByOrderByPinOrderAsc(anyLong());
        List<Long> pins = List.of(2L, 1L, 3L);

        //유저가 소유주가 아닌 경우 or
        doReturn(PortfolioFixture.createPortfolioFixtures_2_1()).when(portfolioRepository)
                .findAllByIdInAndCreatedBy(eq(pins), anyLong());
        //when then
        Assertions.assertThatThrownBy(() -> portfolioService.changePinPortfoliosByIds(1L, pins))
                .isExactlyInstanceOf(PortfolioException.class)
                .hasMessage(NOT_FOUND_PORTFOLIO.message());
    }


}
