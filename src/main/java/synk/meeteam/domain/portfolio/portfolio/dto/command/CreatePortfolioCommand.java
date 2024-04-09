package synk.meeteam.domain.portfolio.portfolio.dto.command;

import java.time.LocalDate;
import java.util.List;
import synk.meeteam.global.entity.ProceedType;

public record CreatePortfolioCommand(
        String title,
        String description,
        String content,
        Long fieldId,
        Long roleId,
        LocalDate proceedStart,
        LocalDate proceedEnd,
        ProceedType proceedType,
        String mainImageFileName,
        String zipFileName,
        List<String> fileOrder
) {
}
