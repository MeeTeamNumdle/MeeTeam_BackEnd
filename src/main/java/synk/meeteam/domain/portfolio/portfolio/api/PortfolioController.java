package synk.meeteam.domain.portfolio.portfolio.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synk.meeteam.domain.portfolio.portfolio.dto.request.CreatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.request.UpdatePortfolioRequestDto;
import synk.meeteam.domain.portfolio.portfolio.dto.response.GetPortfolioResponseDto;
import synk.meeteam.domain.portfolio.portfolio.service.PortfolioFacade;
import synk.meeteam.domain.user.user.entity.User;
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
                                                @RequestBody UpdatePortfolioRequestDto requestDto) {
        return ResponseEntity.ok(1L);
    }
}
