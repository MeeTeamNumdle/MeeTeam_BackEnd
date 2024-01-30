package synk.meeteam.domain.recruitment.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import synk.meeteam.domain.meeteam.entity.Meeteam;

public record CreateRecruitmentPostRequestDto(
        @NotBlank Meeteam meeteam,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank LocalDateTime deadline

) {
    public static CreateRecruitmentPostRequestDto of(final Meeteam meeteam, final String title, final String content,
                                                     final LocalDateTime deadline){
        return new CreateRecruitmentPostRequestDto(meeteam, title, content, deadline);
    }
}
