package synk.meeteam.domain.recruitment.recruitment_post.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import synk.meeteam.domain.common.course.entity.Course;
import synk.meeteam.domain.common.course.entity.Professor;
import synk.meeteam.domain.common.field.entity.Field;
import synk.meeteam.domain.common.role.entity.Role;
import synk.meeteam.domain.common.skill.entity.Skill;
import synk.meeteam.domain.common.tag.entity.Tag;
import synk.meeteam.domain.common.tag.entity.TagType;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitmentApplicant;
import synk.meeteam.domain.recruitment.recruitment_comment.entity.RecruitmentComment;
import synk.meeteam.domain.recruitment.recruitment_post.dto.request.CreateCommentRequestDto;
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

    @Mapping(source = "requestDto.scope", target = "scope", qualifiedByName = "scopeToEnum")
    @Mapping(source = "requestDto.category", target = "category", qualifiedByName = "categoryToEnum")
    @Mapping(source = "requestDto.proceedType", target = "proceedType", qualifiedByName = "proceedTypeToEnum")
    RecruitmentPost toRecruitmentEntity(CreateRecruitmentPostRequestDto requestDto, Field field, Course course,
                                        Professor professor, boolean isCourse);

    RecruitmentRole toRecruitmentRoleEntity(RecruitmentPost recruitmentPost, Role role, int count);

    RecruitmentRoleSkill toRecruitmentSkillEntity(RecruitmentRole recruitmentRole, Skill skill);

    RecruitmentTag toRecruitmentTagEntity(RecruitmentPost recruitmentPost, Tag tag);

    @Mapping(source = "user", target = "applicant")
    RecruitmentApplicant toRecruitmentApplicantEntity(RecruitmentPost recruitmentPost, Role role, User user,
                                                      String comment);

    @Mapping(source = "requestDto.content", target = "content")
    RecruitmentComment toRecruitmentCommentEntity(RecruitmentPost recruitmentPost, CreateCommentRequestDto requestDto);

    @Mapping(source = "name", target = "name")
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
