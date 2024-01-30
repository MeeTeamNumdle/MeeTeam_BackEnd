package synk.meeteam.domain.recruitment.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import synk.meeteam.domain.meeteam.entity.Meeteam;

public record CreateRecruitmentPostRequestDto(
        @NotNull Meeteam meeteam,
        @NotNull String title,
        @NotNull String content,
        @NotNull LocalDate deadline

) {
    public static CreateRecruitmentPostRequestDto of(final Meeteam meeteam, final String title, final String content,
                                                     final LocalDate deadline){
        return new CreateRecruitmentPostRequestDto(meeteam, title, content, deadline);
    }
}
