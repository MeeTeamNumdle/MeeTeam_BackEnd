package synk.meeteam.infra.discord.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Footer {

    private final String text;
    private final String iconUrl;
}
