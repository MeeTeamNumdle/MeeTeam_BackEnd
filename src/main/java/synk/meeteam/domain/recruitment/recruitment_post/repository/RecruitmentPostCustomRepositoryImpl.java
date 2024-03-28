package synk.meeteam.domain.recruitment.recruitment_post.repository;

import static synk.meeteam.domain.common.field.entity.QField.field;
import static synk.meeteam.domain.recruitment.bookmark.entity.QBookmark.bookmark;
import static synk.meeteam.domain.recruitment.recruitment_post.entity.QRecruitmentPost.recruitmentPost;
import static synk.meeteam.domain.recruitment.recruitment_role.entity.QRecruitmentRole.recruitmentRole;
import static synk.meeteam.domain.recruitment.recruitment_role_skill.entity.QRecruitmentRoleSkill.recruitmentRoleSkill;
import static synk.meeteam.domain.recruitment.recruitment_tag.entity.QRecruitmentTag.recruitmentTag;
import static synk.meeteam.domain.user.user.entity.QUser.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.recruitment.recruitment_post.dto.SearchCondition;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.QSearchRecruitmentPostsResponseDto;
import synk.meeteam.domain.recruitment.recruitment_post.dto.response.SearchRecruitmentPostsResponseDto;
import synk.meeteam.domain.user.user.entity.QUser;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.Scope;

@Repository
@RequiredArgsConstructor
public class RecruitmentPostCustomRepositoryImpl implements RecruitmentPostCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SearchRecruitmentPostsResponseDto> findBySearchConditionAndKeyword(
            Pageable pageable,
            SearchCondition condition,
            String keyword,
            User user) {

        Long userId = null;
        if (user != null) {
            userId = user.getId();
        }

        List<SearchRecruitmentPostsResponseDto> contents = getPostDtos(pageable, condition, keyword, userId);
        JPAQuery<Long> countQuery = getCount(condition, keyword, userId);
        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    private List<SearchRecruitmentPostsResponseDto> getPostDtos(Pageable pageable, SearchCondition condition,
                                                                String keyword, Long userId) {
        QUser writer = new QUser("writer");
        return queryFactory.select(
                        new QSearchRecruitmentPostsResponseDto(
                                recruitmentPost.id,
                                recruitmentPost.title,
                                recruitmentPost.category.stringValue(),
                                writer.nickname,
                                writer.profileImgFileName,
                                recruitmentPost.deadline.stringValue(),
                                recruitmentPost.scope.stringValue(),
                                isBookmarkUser(userId)
                        )
                )
                .distinct()
                .from(recruitmentPost)
                .leftJoin(writer).on(recruitmentPost.createdBy.eq(writer.id))
                .leftJoin(recruitmentPost.field, field)
                .leftJoin(recruitmentRole).on(recruitmentPost.id.eq(recruitmentRole.recruitmentPost.id))
                .leftJoin(recruitmentRole.recruitmentRoleSkills, recruitmentRoleSkill)
                .leftJoin(recruitmentTag).on(recruitmentPost.id.eq(recruitmentTag.recruitmentPost.id))
                .leftJoin(bookmark).on(recruitmentPost.id.eq(bookmark.recruitmentPost.id))
                .leftJoin(user).on(bookmark.user.id.eq(user.id))
                .where(
                        categoryEq(condition.getCategory()),
                        scopeEq(condition.getScope(), userId),
                        fieldIdEq(condition.getFieldId()),
                        tagIdIn(condition.getTagIds()),
                        skillIdIn(condition.getSkillIds()),
                        roleIdIn(condition.getRoleIds()),
                        skillIdIn(condition.getSkillIds()),
                        titleContain(keyword)
                )
                .orderBy(recruitmentPost.createdAt.desc())
                .offset(pageable.getOffset()) //페이지 번호
                .limit(pageable.getPageSize()) //페이지 사이즈
                .fetch();
    }

    private JPAQuery<Long> getCount(SearchCondition condition, String keyword, Long userId) {
        QUser writer = new QUser("writer");

        return queryFactory.select(recruitmentPost.countDistinct())
                .from(recruitmentPost)
                .leftJoin(writer).on(recruitmentPost.createdBy.eq(writer.id))
                .leftJoin(recruitmentPost.field, field)
                .leftJoin(recruitmentRole).on(recruitmentPost.id.eq(recruitmentRole.recruitmentPost.id))
                .leftJoin(recruitmentRole.recruitmentRoleSkills, recruitmentRoleSkill)
                .leftJoin(recruitmentTag).on(recruitmentPost.id.eq(recruitmentTag.recruitmentPost.id))
                .leftJoin(bookmark).on(recruitmentPost.id.eq(bookmark.recruitmentPost.id))
                .leftJoin(user).on(bookmark.user.id.eq(user.id))
                .where(
                        categoryEq(condition.getCategory()),
                        scopeEq(condition.getScope(), userId),
                        fieldIdEq(condition.getFieldId()),
                        tagIdIn(condition.getTagIds()),
                        skillIdIn(condition.getSkillIds()),
                        roleIdIn(condition.getRoleIds()),
                        skillIdIn(condition.getSkillIds()),
                        titleContain(keyword)
                );
    }

    private BooleanExpression categoryEq(Category category) {
        return category == null ? null : recruitmentPost.category.eq(category);
    }

    private BooleanExpression scopeEq(Scope scope, Long userId) {
        if (userId == null) {
            return recruitmentPost.scope.eq(Scope.OFF_CAMPUS);
        } else if (scope == null) {
            return null;
        } else {
            return recruitmentPost.scope.eq(scope);
        }
    }

    private BooleanExpression fieldIdEq(Long fieldId) {
        return fieldId == null ? null : recruitmentPost.field.id.eq(fieldId);
    }

    private BooleanExpression skillIdIn(List<Long> skillIds) {
        return (skillIds == null || skillIds.isEmpty()) ? null : recruitmentRoleSkill.id.in(skillIds);
    }

    private BooleanExpression tagIdIn(List<Long> tagIds) {
        return (tagIds == null || tagIds.isEmpty()) ? null : recruitmentTag.id.in(tagIds);
    }

    private BooleanExpression roleIdIn(List<Long> roleIds) {
        return (roleIds == null || roleIds.isEmpty()) ? null : recruitmentRole.id.in(roleIds);
    }

    private BooleanExpression titleContain(String keyword) {
        return (keyword == null || keyword.isEmpty()) ? null : recruitmentPost.title.contains(keyword);
    }

    private BooleanExpression isBookmarkUser(Long userId) {
        return userId == null ? isFalse() : bookmark.isNotNull().and(user.id.eq(userId));
    }

    private BooleanExpression isFalse() {
        return Expressions.asBoolean(true).isFalse();
    }
}
