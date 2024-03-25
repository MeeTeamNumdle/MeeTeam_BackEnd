package synk.meeteam.domain.user.user.dto.response;

import java.time.LocalDate;

public record GetProfileAwardDto(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        String description
) {


}
