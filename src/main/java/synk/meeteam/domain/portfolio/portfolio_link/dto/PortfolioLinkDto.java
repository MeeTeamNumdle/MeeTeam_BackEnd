package synk.meeteam.domain.portfolio.portfolio_link.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PortfolioLinkDto(
        @Schema(description = "url", example = "https://~~")
        @Pattern(regexp = "^https?://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(?::\\d{1,5})?(?:/[a-zA-Z0-9-._?&=%]*)?$")
        String url,
        @Schema(description = "부연설명", example = "Github")
        @Size(max = 20)
        String description
) {
}
