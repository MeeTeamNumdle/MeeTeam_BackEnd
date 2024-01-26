package synk.meeteam.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.entity.enums.PlatformType;

public record AuthUserRequestDto(@NotNull PlatformType platformType) {
}