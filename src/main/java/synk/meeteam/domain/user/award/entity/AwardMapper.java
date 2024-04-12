package synk.meeteam.domain.user.award.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import synk.meeteam.domain.user.award.dto.GetProfileAwardDto;
import synk.meeteam.domain.user.award.dto.UpdateAwardDto;

@Mapper(componentModel = "spring")
public interface AwardMapper {

    @Mapping(source = "updateAwardDto.startDate", target = "proceedingStart")
    @Mapping(source = "updateAwardDto.endDate", target = "proceedingEnd")
    Award toAward(UpdateAwardDto updateAwardDto);

    @Mapping(source = "award.proceedingStart", target = "startDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "award.proceedingEnd", target = "endDate", dateFormat = "yyyy-MM-dd")
    GetProfileAwardDto toGetProfileAwardDto(Award award);
}
