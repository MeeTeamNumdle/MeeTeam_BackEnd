package synk.meeteam.domain.recruitment.recruitment_post.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateRecruitmentPostRequestDto;
import synk.meeteam.domain.recruitment.recruitment_post.entity.RecruitmentPost;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.ProceedType;
import synk.meeteam.global.entity.Scope;

@Mapper(componentModel = "spring")
public interface RecruitmentPostMapper {
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

    @Mapping(source = "requestDto.scope", target = "scope", qualifiedByName = "scopeToEnum")
    @Mapping(source = "requestDto.category", target = "category", qualifiedByName = "categoryToEnum")
    @Mapping(source = "requestDto.proceedType", target = "proceedType", qualifiedByName = "proceedTypeToEnum")
    RecruitmentPost toRecruitmentEntity(CreateRecruitmentPostRequestDto requestDto, Field field);
}
