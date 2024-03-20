package synk.meeteam.domain.user.award;

import java.time.LocalDate;
import java.util.List;
import synk.meeteam.domain.user.award.dto.UpdateAwardDto;
import synk.meeteam.domain.user.award.entity.Award;

public class AwardFixture {
    public static List<Award> createAwardFixture() {
        return List.of(
                new Award(1L, "수상1"),
                new Award(2L, "수상2"),
                new Award(3L, "수상3")
        );
    }


    public static List<UpdateAwardDto> createAwardDtoFixture() {
        return List.of(
                new UpdateAwardDto("수상1", "디스크립션1", LocalDate.of(2023, 12, 15), LocalDate.of(2023, 12, 16)),
                new UpdateAwardDto("수상2", "디스크립션2", LocalDate.of(2023, 12, 15), LocalDate.of(2023, 12, 16)),
                new UpdateAwardDto("수상3", "디스크립션3", LocalDate.of(2023, 12, 15), LocalDate.of(2023, 12, 16))
        );
    }
}
