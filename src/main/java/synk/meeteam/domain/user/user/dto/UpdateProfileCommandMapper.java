package synk.meeteam.domain.user.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import synk.meeteam.domain.user.user.dto.command.UpdateInfoCommand;
import synk.meeteam.domain.user.user.dto.request.UpdateProfileRequestDto;

@Mapper(componentModel = "spring")
public interface UpdateProfileCommandMapper {

    @Mapping(source = "imageFileName", target = "pictureFileName")
    @Mapping(source = "introduction", target = "oneLineIntroduction")
    @Mapping(source = "aboutMe", target = "mainIntroduction")
    UpdateInfoCommand toUpdateProfileCommand(UpdateProfileRequestDto updateProfileRequestDto);
}
