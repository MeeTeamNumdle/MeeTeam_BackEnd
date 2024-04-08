package synk.meeteam.domain.portfolio.portfolio;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.field.repository.FieldRepository;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.repository.RoleRepository;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;
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

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FieldRepository fieldRepository;

    @BeforeEach
    void setup() {
        Role role = roleRepository.findById(1L).get();
        Field field = fieldRepository.findById(1L).get();

        List<Portfolio> portfolios = List.of(
                PortfolioFixture.createUserPortfolio("타이틀1", true, 1, 1L, LocalDate.of(2024, 1, 2), role, field),
                PortfolioFixture.createUserPortfolio("타이틀2", true, 2, 1L, LocalDate.of(2024, 1, 3), role, field),
                PortfolioFixture.createUserPortfolio("타이틀3", false, 3, 1L, LocalDate.of(2024, 1, 4), role, field),
                PortfolioFixture.createUserPortfolio("타이틀4", true, 1, 2L, LocalDate.of(2024, 1, 4), role, field)
        );
        portfolioRepository.saveAllAndFlush(portfolios);
    }

    @Test
    void 특정유저의핀포트폴리오목록조회_특정유저의핀포트폴리오목록반환() {
        //when
        List<Portfolio> portfolios = portfolioRepository.findAllByCreatedByAndIsPinTrue(1L);
        //then
        assertThat(portfolios).extracting("title").containsExactly("타이틀1", "타이틀2");
    }

    @Test
    void 나의핀포트폴리오조회_조회성공() {
        //when
        List<Portfolio> userPortfolios = portfolioRepository.findAllByCreatedByAndIsPinTrueOrderByIds(1L,
                List.of(1L, 2L));
        //then
        assertThat(userPortfolios).extracting("title").containsExactly("타이틀1", "타이틀2");

    }

    @Test
    void 나의포트폴리오모두조회_조회성공() {
        //given
        User user = userRepository.findById(1L).get();
        //when
        Slice<GetProfilePortfolioDto> userPortfolios = portfolioRepository.findUserPortfoliosByUserOrderByCreatedAtDesc(
                PageRequest.of(0, 12), user);
        //then
        assertThat(userPortfolios.hasNext()).isEqualTo(false);
        assertThat(userPortfolios.getSize()).isEqualTo(12);
        assertThat(userPortfolios.getContent()).extracting("title").containsExactly("타이틀3", "타이틀2", "타이틀1");
    }
}
