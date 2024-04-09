package synk.meeteam.domain.portfolio.portfolio.api;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController implements PortfolioApi {

    @PostMapping
    @Override
    public ResponseEntity<Long> postPortfolio(@RequestBody CreatePortfolioRequestDto requestDto) {
        return null;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GetPortfolioResponseDto> getPortfolio(@PathVariable("id") Long portfolioId) {
        return null;
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Long> modifyPortfolio(@PathVariable("id") Long portfolioId,
                                                @RequestBody UpdatePortfolioRequestDto requestDto) {
        return null;
    }
}
