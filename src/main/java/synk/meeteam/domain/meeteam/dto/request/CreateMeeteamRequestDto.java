package synk.meeteam.domain.meeteam.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import synk.meeteam.domain.field.entity.Field;
import synk.meeteam.domain.meeteam.entity.MeeteamCategory;
import synk.meeteam.domain.meeteam.entity.MeeteamProceed;
import synk.meeteam.domain.meeteam.entity.MeeteamScope;
import synk.meeteam.domain.tag.entity.Tag;

public record CreateMeeteamRequestDto(
        @NotNull Long leaderId,
        @NotNull String name,
        String introduction,
        @NotNull Boolean isRecruiting,
        @NotNull Boolean isCourse,
        @NotNull MeeteamScope meeteamScope,
        @NotNull MeeteamCategory meeteamCategory,
        @NotNull MeeteamProceed meeteamProceed,
        @NotNull LocalDate proceedingStart,
        @NotNull LocalDate proceedingEnd,
        @NotNull Boolean isPublic,
        Field field,
        @NotNull String coverImgUrl,
        @NotNull List<Tag> meeteamTags
) {
    public static CreateMeeteamRequestDto of(final Long leaderId, final String name, final String introduction,
                                             final Boolean isRecruiting,
                                             final Boolean isCourse, final MeeteamScope meeteamScope,
                                             final MeeteamCategory meeteamCategory,
                                             final MeeteamProceed meeteamProceed, final LocalDate proceedingStart,
                                             final LocalDate proceedingEnd, final Boolean isPublic,
                                             final Field field, final String coverImgUrl,
                                             final List<Tag> meeteamTags) {

        return new CreateMeeteamRequestDto(leaderId, name, introduction, isRecruiting, isCourse, meeteamScope,
                meeteamCategory,
                meeteamProceed, proceedingStart, proceedingEnd, isPublic, field, coverImgUrl, meeteamTags);
    }
}
