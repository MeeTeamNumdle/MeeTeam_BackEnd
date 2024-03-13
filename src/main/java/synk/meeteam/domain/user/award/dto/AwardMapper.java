package synk.meeteam.domain.user.award.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import synk.meeteam.domain.user.award.entity.Award;
import synk.meeteam.domain.user.user.entity.User;

@Mapper(componentModel = "spring")
public interface AwardMapper {


    @Mapping(source = "updateAwardDto.startDate", target = "proceedingStart")
    @Mapping(source = "updateAwardDto.endDate", target = "proceedingEnd")
    @Mapping(source = "user", target = "user")
    Award toAward(User user, UpdateAwardDto updateAwardDto);
}
