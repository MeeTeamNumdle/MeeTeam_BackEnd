package synk.meeteam.domain.user.award;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import synk.meeteam.domain.user.award.entity.Award;
import synk.meeteam.domain.user.award.entity.AwardMapper;
import synk.meeteam.domain.user.award.repository.AwardRepository;
import synk.meeteam.domain.user.award.service.AwardService;

@ExtendWith(MockitoExtension.class)
public class AwardServiceTest {

    @InjectMocks
    AwardService awardService;

    @Mock
    AwardRepository awardRepository;

    @Spy
    AwardMapper awardMapper = Mappers.getMapper(AwardMapper.class);
    ;

    @Test
    void 수상목록변경_수상목록변경성공() {
        //given
        doNothing().when(awardRepository).deleteAllByCreatedBy(anyLong());
        doReturn(AwardFixture.createAwardFixture()).when(awardRepository).saveAll(Mockito.<Award>anyList());
        //when
        List<Award> awards = awardService.changeAward(1L, AwardFixture.createAwardDtoFixture());

        //then
        Assertions.assertThat(awards.size()).isEqualTo(3);
        Assertions.assertThat(awards).extracting("title").containsExactly("수상1", "수상2", "수상3");
    }

}
