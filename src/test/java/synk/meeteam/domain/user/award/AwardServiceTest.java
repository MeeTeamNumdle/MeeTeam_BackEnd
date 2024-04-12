package synk.meeteam.domain.user.award;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
import synk.meeteam.domain.user.award.service.AwardServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AwardServiceTest {

    @Mock
    AwardService awardService;

    @InjectMocks
    AwardServiceImpl awardServiceImpl;

    @Mock
    AwardRepository awardRepository;

    @Spy
    AwardMapper awardMapper = Mappers.getMapper(AwardMapper.class);

    @BeforeEach
    void setup() {
        awardService = awardServiceImpl;
    }

    @Test
    void 수상목록변경_수상목록변경성공() {

        //given
        doNothing().when(awardRepository).deleteAllByCreatedBy(anyLong());
        doReturn(AwardFixture.createAwardFixture()).when(awardRepository).saveAll(Mockito.<Award>anyList());
        //when
        List<Award> awards = awardService.changeAward(1L, AwardFixture.createAwardDtoFixture());

        //then
        assertThat(awards.size()).isEqualTo(3);
        assertThat(awards).extracting("title").containsExactly("수상1", "수상2", "수상3");
    }

    @Test
    void 수상목록조회_수상목록조회성공() {
        //given
        doReturn(AwardFixture.createAwardDtoFixture()).when(awardRepository).findAllByCreatedBy(anyLong());
        //when
        List<Award> awards = awardService.getAward(1L);
        //then
        assertThat(awards).extracting("title").containsExactly("수상1", "수상2", "수상3");
    }

}
