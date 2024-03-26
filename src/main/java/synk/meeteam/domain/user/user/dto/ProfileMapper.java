package synk.meeteam.domain.user.user.dto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;
import synk.meeteam.domain.user.award.dto.GetProfileAwardDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileEmailDto;
import synk.meeteam.domain.user.user.dto.response.GetProfilePhoneDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user_link.dto.GetProfileUserLinkDto;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(source = "user", target = "universityEmail", qualifiedByName = "toUniversityEmail")
    @Mapping(source = "user", target = "subEmail", qualifiedByName = "toSubEmail")
    @Mapping(source = "user", target = "phone", qualifiedByName = "toPhone")
    @Mapping(source = "user", target = "interest", qualifiedByName = "toRole")
    @Mapping(source = "user.isPublicName", target = "isUserNamePublic")
    @Mapping(source = "user.oneLineIntroduction", target = "introduction")
    @Mapping(source = "user.mainIntroduction", target = "aboutMe")
    @Mapping(source = "user.admissionYear", target = "year")
    @Mapping(source = "user", target = "university", qualifiedByName = "toUniversity")
    @Mapping(source = "user", target = "department", qualifiedByName = "toDepartment")
    GetProfileResponseDto toGetProfileResponseDto(
            User user,
            String imageUrl,
            List<GetProfileUserLinkDto> links,
            List<GetProfileAwardDto> awards,
            List<GetProfilePortfolioDto> portfolios,
            List<SkillDto> skills
    );


    @Named("toUniversityEmail")
    @Mapping(source = "user.universityEmail", target = "content")
    @Mapping(source = "user.isPublicUniversityEmail", target = "isPublic")
    @Mapping(source = "user.isUniversityMainEmail", target = "isDefault")
    GetProfileEmailDto toUniversityEmail(User user);


    @Named("toSubEmail")
    @Mapping(source = "subEmail", target = "content")
    @Mapping(source = "isPublicSubEmail", target = "isPublic")
    @Mapping(expression = "java(!user.getIsUniversityMainEmail())", target = "isDefault")
    GetProfileEmailDto toSubEmail(User user);

    @Named("toPhone")
    @Mapping(source = "phoneNumber", target = "content")
    @Mapping(source = "isPublicPhone", target = "isPublic")
    GetProfilePhoneDto tuPhone(User user);

    @Named("toRole")
    @Mapping(source = "interestRole.name", target = ".")
    String toRole(User user);

    @Named("toUniversity")
    @Mapping(source = "university.name", target = ".")
    String toUniversity(User user);

    @Named("toDepartment")
    @Mapping(source = "department.name", target = ".")
    String toDepartment(User user);

}
