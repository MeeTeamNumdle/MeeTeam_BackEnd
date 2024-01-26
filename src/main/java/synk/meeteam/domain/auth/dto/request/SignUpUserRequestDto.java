package synk.meeteam.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.entity.enums.PlatformType;

public record SignUpUserRequestDto(
    @NotNull String platformId,
    @NotNull PlatformType platformType,
    @NotNull String email,
    @NotNull String universityName,
    @NotNull String departmentName,
    @NotNull int admissionYear

) {
}
