package synk.meeteam.domain.portfolio.portfolio.dto.command;

import java.time.LocalDate;
import java.util.List;
import synk.meeteam.global.entity.ProceedType;

public record UpdatePortfolioCommand(
        String title,
        String description,
        String content,
        Long fieldId,
        Long roleId,
        LocalDate proceedStart,
        LocalDate proceedEnd,
        ProceedType proceedType,
        List<String> fileOrder,
        String mainImageFileName
) {

}
