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
    @Mapping(source = "userName", target = "name")
    @Mapping(source = "phone", target = "phoneNumber")
    @Mapping(source = "isPhonePublic", target = "isPublicPhone")
    @Mapping(source = "isSubEmailPublic", target = "isPublicSubEmail")
    @Mapping(source = "isUniversityEmailPublic", target = "isPublicUniversityEmail")
    @Mapping(source = "isUniversityMain", target = "isUniversityMainEmail")
    UpdateInfoCommand toUpdateProfileCommand(UpdateProfileRequestDto updateProfileRequestDto);
}
