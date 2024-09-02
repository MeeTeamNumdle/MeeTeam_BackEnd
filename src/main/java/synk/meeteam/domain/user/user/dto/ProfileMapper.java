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
import synk.meeteam.domain.user.user.dto.response.ProfileDto;
import synk.meeteam.domain.user.user_link.dto.GetProfileUserLinkDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProfileMapper {
    @Mapping(source = "profile", target = "universityEmail", qualifiedByName = "toUniversityEmail")
    @Mapping(source = "profile", target = "subEmail", qualifiedByName = "toSubEmail")
    @Mapping(source = "profile", target = "phone", qualifiedByName = "toPhone")
    @Mapping(source = "profile.name", target = "userName")
    @Mapping(source = "profile.isPublicName", target = "isUserNamePublic")
    @Mapping(source = "profile.oneLineIntroduction", target = "introduction")
    @Mapping(source = "profile.mainIntroduction", target = "aboutMe")
    @Mapping(source = "profile.admissionYear", target = "year")
    @Mapping(source = "profile.profileImgFileName", target = "imageFileName")
    GetProfileResponseDto toGetProfileResponseDto(
            ProfileDto profile,
            String imageUrl,
            List<GetProfileUserLinkDto> links,
            List<GetProfileAwardDto> awards,
            List<SimplePortfolioDto> portfolios,
            List<SkillDto> skills
    );


    @Named("toUniversityEmail")
    @Mapping(source = "profile.universityEmail", target = "content")
    @Mapping(source = "profile.isPublicUniversityEmail", target = "isPublic")
    @Mapping(source = "profile.isUniversityMainEmail", target = "isDefault")
    GetProfileEmailDto toUniversityEmail(ProfileDto profile);


    @Named("toSubEmail")
    @Mapping(source = "subEmail", target = "content")
    @Mapping(source = "isPublicSubEmail", target = "isPublic")
    @Mapping(expression = "java(!profile.isUniversityMainEmail())", target = "isDefault")
    GetProfileEmailDto toSubEmail(ProfileDto profile);

    @Named("toPhone")
    @Mapping(source = "phoneNumber", target = "content")
    @Mapping(source = "isPublicPhone", target = "isPublic")
    GetProfilePhoneDto tuPhone(ProfileDto profile);

}
