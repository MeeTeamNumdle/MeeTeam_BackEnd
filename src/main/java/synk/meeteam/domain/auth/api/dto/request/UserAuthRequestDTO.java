package synk.meeteam.domain.auth.api.dto.request;

import jakarta.validation.constraints.NotNull;
import synk.meeteam.domain.user.entity.enums.PlatformType;

public record UserAuthRequestDTO(@NotNull PlatformType platformType) {
}