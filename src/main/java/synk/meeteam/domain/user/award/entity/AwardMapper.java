package synk.meeteam.domain.user.award.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import synk.meeteam.domain.user.award.dto.UpdateAwardDto;

@Mapper(componentModel = "spring")
public interface AwardMapper {

    @Mapping(source = "updateAwardDto.startDate", target = "proceedingStart")
    @Mapping(source = "updateAwardDto.endDate", target = "proceedingEnd")
    Award toAward(UpdateAwardDto updateAwardDto);
}
