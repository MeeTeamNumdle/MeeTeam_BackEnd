package synk.meeteam.infra.discord.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Field {

    private final String name;
    private final String value;
    private final boolean inline;
}
