package synk.meeteam.domain.meeteam.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import synk.meeteam.domain.field.entity.Field;
import synk.meeteam.domain.meeteam.entity.MeeteamCategory;
import synk.meeteam.domain.meeteam.entity.MeeteamProceed;
import synk.meeteam.domain.meeteam.entity.MeeteamScope;
import synk.meeteam.domain.tag.entity.Tag;

public record CreateMeeteamRequestDto(
        @NotBlank Long leaderId,
        @NotBlank String name,
        String introduction,
        @NotBlank Boolean isRecruiting,
        @NotBlank Boolean isCourse,
        @NotBlank MeeteamScope meeteamScope,
        @NotBlank MeeteamCategory meeteamCategory,
        @NotBlank MeeteamProceed meeteamProceed,
        @NotBlank LocalDateTime proceedingStart,
        @NotBlank LocalDateTime proceedingEnd,
        @NotBlank Boolean isPublic,
        @NotBlank Field field,
        @NotBlank String coverImgUrl,
        @NotBlank List<Tag> meeteamTags
) {
    public static CreateMeeteamRequestDto of(final Long leaderId, final String name, final String introduction,
                                             final Boolean isRecruiting,
                                             final Boolean isCourse, final MeeteamScope meeteamScope,
                                             final MeeteamCategory meeteamCategory,
                                             final MeeteamProceed meeteamProceed, final LocalDateTime proceedingStart,
                                             final LocalDateTime proceedingEnd, final Boolean isPublic,
                                             final Field field, final String coverImgUrl,
                                             final List<Tag> meeteamTags) {

        return new CreateMeeteamRequestDto(leaderId, name, introduction, isRecruiting, isCourse, meeteamScope,
                meeteamCategory,
                meeteamProceed, proceedingStart, proceedingEnd, isPublic, field, coverImgUrl, meeteamTags);
    }
}
