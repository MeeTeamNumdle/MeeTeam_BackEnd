package synk.meeteam.domain.user.user.dto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import synk.meeteam.domain.common.skill.dto.SkillDto;
import synk.meeteam.domain.portfolio.portfolio.dto.SimplePortfolioDto;
import synk.meeteam.domain.user.award.dto.GetProfileAwardDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileEmailDto;
import synk.meeteam.domain.user.user.dto.response.GetProfilePhoneDto;
import synk.meeteam.domain.user.user.dto.response.GetProfileResponseDto;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.domain.user.user_link.dto.GetProfileUserLinkDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProfileMapper {
    @Mapping(source = "user", target = "universityEmail", qualifiedByName = "toUniversityEmail")
    @Mapping(source = "user", target = "subEmail", qualifiedByName = "toSubEmail")
    @Mapping(source = "user", target = "phone", qualifiedByName = "toPhone")
    @Mapping(source = "user.interestRole.name", target = "interest")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.isPublicName", target = "isUserNamePublic")
    @Mapping(source = "user.oneLineIntroduction", target = "introduction")
    @Mapping(source = "user.mainIntroduction", target = "aboutMe")
    @Mapping(source = "user.admissionYear", target = "year")
    @Mapping(source = "user.university.name", target = "university")
    @Mapping(source = "user.department.name", target = "department")
    @Mapping(source = "user.profileImgFileName", target = "imageFileName")
    GetProfileResponseDto toGetProfileResponseDto(
            User user,
            String imageUrl,
            List<GetProfileUserLinkDto> links,
            List<GetProfileAwardDto> awards,
            List<SimplePortfolioDto> portfolios,
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
    @Mapping(expression = "java(!user.isUniversityMainEmail())", target = "isDefault")
    GetProfileEmailDto toSubEmail(User user);

    @Named("toPhone")
    @Mapping(source = "phoneNumber", target = "content")
    @Mapping(source = "isPublicPhone", target = "isPublic")
    GetProfilePhoneDto tuPhone(User user);

}
