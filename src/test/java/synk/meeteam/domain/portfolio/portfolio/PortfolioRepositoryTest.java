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
import org.springframework.test.context.jdbc.Sql;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.field.repository.FieldRepository;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.role.repository.RoleRepository;
import synk.meeteam.domain.portfolio.portfolio.dto.SimplePortfolioDto;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.portfolio.portfolio.repository.PortfolioRepository;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user.repository.UserRepository;
import synk.meeteam.global.entity.ProceedType;

@DataJpaTest
@ActiveProfiles("test")
@Sql({"classpath:test-portfolio.sql"})
public class PortfolioRepositoryTest {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FieldRepository fieldRepository;
    Role role;
    Field field;

    @BeforeEach
    void setup() {
        role = roleRepository.findById(1L).get();
        field = fieldRepository.findById(1L).get();
    }

    @Test
    void 나의핀포트폴리오목록조회_나의의핀포트폴리오목록반환() {
        //when
        List<Portfolio> portfolios = portfolioRepository.findAllByCreatedByAndIsPinTrue(1L);
        //then
        assertThat(portfolios).extracting("title").containsExactly("타이틀1", "타이틀2");
    }

    @Test
    void 나의핀포트폴리오핀순서로조회_조회성공() {
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
        int page = 1;
        //when
        Slice<SimplePortfolioDto> userPortfolios = portfolioRepository.findSlicePortfoliosByUserOrderByCreatedAtDesc(
                PageRequest.of(page - 1, 12), user);
        //then
        assertThat(userPortfolios.hasNext()).isEqualTo(false);
        assertThat(userPortfolios.getSize()).isEqualTo(12);
        assertThat(userPortfolios.getContent()).extracting("title").containsExactly("타이틀3", "타이틀2", "타이틀1");
    }

    @Test
    void 포트폴리오등록_등록성공() {
        //given
        Portfolio portfolio = Portfolio.builder()
                .title("Meeteam")
                .description("대학생 맞춤형 구인 포트폴리오 서비스")
                .content("밋팀(Meeteam)은 나 자신을 의미하는 Me, 팀을 의미하는 Team, 만남을 의미하는 Meet이 합쳐진 단어입니다.\n"
                        + "대학생들의 보다 원활한 팀프로젝트를 위해 기획하게 되었습니다.\n"
                        + "\n"
                        + "\n"
                        + "밋팀(Meeteam)은 나 자신을 의미하는 Me, 팀을 의미하는 Team, 만남을 의미하는 Meet이 합쳐진 단어입니다.\n"
                        + "대학생들의 보다 원활한 팀프로젝트를 위해 기획하게 되었으며, 그 외에 포토폴리오로서의 기능까지 생각하고 있습니다!\n"
                        + "\n"
                        + "이를 위해 함께 멋진 서비스를 완성할 웹 디자이너를 찾고 있어요!\n"
                        + "밋팀(Meeteam)은 나 자신을 의미하는 Me, 팀을 의미하는 Team, 만남을 의미하는 Meet이 합쳐진 단어입니다.\n"
                        + "대학생들의 보다 원활한 팀프로젝트를 위해 기획하게 되었으며, 그 외에 포토폴리오로서의 기능까지 생각하고 있습니다!\n")
                .proceedStart(LocalDate.of(2023, 11, 2))
                .proceedEnd(LocalDate.of(2024, 3, 15))
                .proceedType(ProceedType.ON_LINE)
                .field(field)
                .role(role)
                .mainImageFileName("sdkfjldkfcjemqq.png")
                .zipFileName("sdkflsdfjfklwemq.zip")
                .fileOrder(List.of("이미지1.png", "이미지2.png"))
                .build();
        //when
        Portfolio savedPortfolio = portfolioRepository.saveAndFlush(portfolio);

        //then
        assertThat(savedPortfolio.getId()).isNotNull();
        assertThat(savedPortfolio.getTitle()).isEqualTo(portfolio.getTitle());
        assertThat(savedPortfolio.getContent()).isEqualTo(portfolio.getContent());
        assertThat(savedPortfolio.getProceedStart()).isEqualTo(portfolio.getProceedStart());
        assertThat(savedPortfolio.getProceedEnd()).isEqualTo(portfolio.getProceedEnd());
        assertThat(savedPortfolio.getMainImageFileName()).isEqualTo(portfolio.getMainImageFileName());
    }
}
