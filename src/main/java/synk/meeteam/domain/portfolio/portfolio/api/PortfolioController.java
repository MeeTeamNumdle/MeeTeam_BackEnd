package synk.meeteam.domain.portfolio.portfolio.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.portfolio.portfolio.dto.SimplePortfolioDto;
import synk.meeteam.domain.portfolio.portfolio.dto.request.CreatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.request.UpdatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.response.GetPortfolioResponseDto;
import synk.meeteam.domain.portfolio.portfolio.service.PortfolioFacade;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.dto.PageInfo;
import synk.meeteam.global.dto.PageNationDto;
import synk.meeteam.security.AuthUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController implements PortfolioApi {

    private final PortfolioFacade portfolioFacade;

    @PostMapping
    @Override
    public ResponseEntity<Long> postPortfolio(@RequestBody @Valid CreatePortfolioRequestDto requestDto) {
        Long portfolioId = portfolioFacade.postPortfolio(requestDto);
        return ResponseEntity.ok(portfolioId);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GetPortfolioResponseDto> getPortfolio(@PathVariable("id") Long portfolioId,
                                                                @AuthUser User user) {
        return ResponseEntity.ok(portfolioFacade.getPortfolio(portfolioId, user));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Long> modifyPortfolio(@AuthUser User user, @PathVariable("id") Long portfolioId,
                                                @RequestBody @Valid UpdatePortfolioRequestDto requestDto) {
        return ResponseEntity.ok(portfolioFacade.editPortfolio(portfolioId, user, requestDto));
    }

    @GetMapping
    @Override
    public ResponseEntity<PageNationDto<SimplePortfolioDto>> getMyPortfolios(
            @RequestParam(value = "size", required = false, defaultValue = "24") @Valid @Min(1) int size,
            @RequestParam(value = "page", required = false, defaultValue = "1") @Valid @Min(1) int page,
            @AuthUser User user) {
        return ResponseEntity.ok().body(new PageNationDto<>(
                List.of(
                        new SimplePortfolioDto(
                                1L,
                                "Meeteam",
                                "https://image.png",
                                "개발",
                                "역할",
                                true,
                                1
                        )
                ),
                new PageInfo(1, 24, 1L, 1)
        ));
    }
}
