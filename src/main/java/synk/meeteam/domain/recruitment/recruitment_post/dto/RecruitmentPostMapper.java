package synk.meeteam.domain.recruitment.recruitment_post.dto;

import java.time.LocalDate;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.domain.recruitment.recruitment_role.entity.RecruitmentRole;
import synk.meeteam.domain.recruitment.recruitment_role_skill.entity.RecruitmentRoleSkill;
import synk.meeteam.domain.recruitment.recruitment_tag.entity.RecruitmentTag;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;

@Mapper(componentModel = "spring")
public interface RecruitmentPostMapper {

    int START = 0;
    int END = 1;

    @Named("proceedingPeriodToProceedingStart")
    static LocalDate proceedingPeriodToProceedingStart(List<LocalDate> proceedingPeriod) {
        return proceedingPeriod.get(START);
    }

    @Named("proceedingPeriodToProceedingEnd")
    static LocalDate proceedingPeriodToProceedingEnd(List<LocalDate> proceedingPeriod) {
        return proceedingPeriod.get(END);
    }

    @Mapping(source = "requestDto.scope", target = "scope", qualifiedByName = "scopeToEnum")
    @Mapping(source = "requestDto.category", target = "category", qualifiedByName = "categoryToEnum")
    @Mapping(source = "requestDto.proceedType", target = "proceedType", qualifiedByName = "proceedTypeToEnum")
    @Mapping(source = "requestDto.proceedingPeriod", target = "proceedingStart", qualifiedByName = "proceedingPeriodToProceedingStart")
    @Mapping(source = "requestDto.proceedingPeriod", target = "proceedingEnd", qualifiedByName = "proceedingPeriodToProceedingEnd")
    RecruitmentPost toRecruitmentEntity(CreateRecruitmentPostRequestDto requestDto, Field field);

    RecruitmentRole toRecruitmentRoleEntity(RecruitmentPost recruitmentPost, Role role, int count);

    RecruitmentRoleSkill toRecruitmentSkillEntity(RecruitmentRole recruitmentRole, Skill skill);

    RecruitmentTag toRecruitmentTagEntity(RecruitmentPost recruitmentPost, Tag tag);

    @Mapping(source = "user", target = "applicant")
    RecruitmentApplicant toRecruitmentApplicantEntity(RecruitmentPost recruitmentPost, Role role, User user,
                                                      String comment);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "type", target = "type")
    Tag toTagEntity(String name, TagType type);

    @Named("scopeToEnum")
    static Scope scopeToEnum(String scope) {
        return Scope.findByName(scope);
    }

    @Named("categoryToEnum")
    static Category categoryToEnum(String category) {
        return Category.findByName(category);
    }

    @Named("proceedTypeToEnum")
    static ProceedType proceedTypeToEnum(String proceedType) {
        return ProceedType.findByName(proceedType);
    }


}
