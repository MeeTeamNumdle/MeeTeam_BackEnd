package synk.meeteam.domain.portfolio.portfolio_link.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PortfolioLinkDto(
        @Schema(description = "url", example = "http://~~")
        String url,
        @Schema(description = "부연설명", example = "Github")
        String description
) {
}
