package synk.meeteam.domain.portfolio.portfolio.api;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;
import synk.meeteam.domain.portfolio.portfolio.dto.request.CreatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.request.UpdatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.response.GetPortfolioResponseDto;
import synk.meeteam.domain.portfolio.portfolio_link.dto.PortfolioLinkDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController implements PortfolioApi {

    @PostMapping
    @Override
    public ResponseEntity<Long> postPortfolio(@RequestBody CreatePortfolioRequestDto requestDto) {
        return ResponseEntity.ok(1L);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GetPortfolioResponseDto> getPortfolio(@PathVariable("id") Long portfolioId) {
        return ResponseEntity.ok(
                new GetPortfolioResponseDto(
                        "Meeteam",
                        "대학생 맞춤형 구인 포트폴리오 서비스",
                        "밋팀(Meeteam)은 나 자신을 의미하는 Me, 팀을 의미하는 Team, 만남을 의미하는 Meet이 합쳐진 단어입니다.\n"
                                + "대학생들의 보다 원활한 팀프로젝트를 위해 기획하게 되었습니다.\n"
                                + "\n"
                                + "\n"
                                + "밋팀(Meeteam)은 나 자신을 의미하는 Me, 팀을 의미하는 Team, 만남을 의미하는 Meet이 합쳐진 단어입니다.\n"
                                + "대학생들의 보다 원활한 팀프로젝트를 위해 기획하게 되었으며, 그 외에 포토폴리오로서의 기능까지 생각하고 있습니다!\n"
                                + "\n"
                                + "이를 위해 함께 멋진 서비스를 완성할 웹 디자이너를 찾고 있어요!\n"
                                + "밋팀(Meeteam)은 나 자신을 의미하는 Me, 팀을 의미하는 Team, 만남을 의미하는 Meet이 합쳐진 단어입니다.\n"
                                + "대학생들의 보다 원활한 팀프로젝트를 위해 기획하게 되었으며, 그 외에 포토폴리오로서의 기능까지 생각하고 있습니다!\n",
                        "https://1.zip",
                        List.of("이미지1.png", "이미지2.png"),
                        "개발",
                        "프론트엔드 개발자",
                        LocalDate.of(2023, 11, 2),
                        LocalDate.of(2024, 3, 15),
                        "오프라인",
                        List.of(
                                new SkillDto(1L, "spring"),
                                new SkillDto(2L, "react")
                        ),
                        List.of(
                                new PortfolioLinkDto("https://github.com/", "Github"),
                                new PortfolioLinkDto("https://naver.com", "Link")
                        ),
                        List.of(
                                new GetProfilePortfolioDto(
                                        1L,
                                        "Meeteam",
                                        "https://이미지1.png",
                                        "개발",
                                        "프론트엔드 개발자",
                                        true,
                                        1
                                ),
                                new GetProfilePortfolioDto(
                                        1L,
                                        "Meeteam",
                                        "https://이미지1.png",
                                        "개발",
                                        "프론트엔드 개발자",
                                        true,
                                        2
                                ),
                                new GetProfilePortfolioDto(
                                        1L,
                                        "Meeteam",
                                        "https://이미지1.png",
                                        "개발",
                                        "프론트엔드 개발자",
                                        true,
                                        3
                                ),
                                new GetProfilePortfolioDto(
                                        1L,
                                        "Meeteam",
                                        "https://이미지1.png",
                                        "개발",
                                        "프론트엔드 개발자",
                                        true,
                                        4
                                )
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Long> modifyPortfolio(@PathVariable("id") Long portfolioId,
                                                @RequestBody UpdatePortfolioRequestDto requestDto) {
        return ResponseEntity.ok(1L);
    }
}
