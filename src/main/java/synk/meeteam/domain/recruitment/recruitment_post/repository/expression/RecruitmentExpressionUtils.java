package synk.meeteam.domain.recruitment.recruitment_post.repository.expression;

import static com.querydsl.jpa.JPAExpressions.selectOne;
import static synk.meeteam.domain.recruitment.bookmark.entity.QBookmark.bookmark;
import static synk.meeteam.domain.recruitment.recruitment_post.entity.QRecruitmentPost.recruitmentPost;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import synk.meeteam.domain.recruitment.recruitment_post.dto.ManageType;
import synk.meeteam.domain.user.user.entity.QUser;
import synk.meeteam.domain.user.user.entity.User;
import synk.meeteam.global.entity.Category;
import synk.meeteam.global.entity.DeleteStatus;
import synk.meeteam.global.entity.Scope;

public class RecruitmentExpressionUtils {
    public static BooleanExpression isBookmark(User userDomain) {
        //로그인 안된 경우
        if (userDomain == null) {
            return isFalse();
        }

        return selectOne()
                .from(bookmark)
                .where(
                        bookmark.user.id.eq(userDomain.getId()),
                        bookmark.recruitmentPost.id.eq(recruitmentPost.id))
                .exists();
    }

    public static BooleanExpression isClosedEq(Boolean isClosed) {
        return isClosed == null ? null : recruitmentPost.isClosed.eq(isClosed);
    }

    public static BooleanExpression writerUniversityEqUser(QUser writer, User userDomain, Scope scope) {
        if (scope == Scope.ON_CAMPUS) {
            return isOnCampus(writer, userDomain);
        } else if (scope == Scope.OFF_CAMPUS) {
            return isOffCampus();
        } else {
            return isOnCampus(writer, userDomain).or(isOffCampus());
        }
    }

    public static BooleanExpression isOffCampus() {
        return recruitmentPost.scope.eq(Scope.OFF_CAMPUS);
    }

    public static BooleanExpression isOnCampus(QUser writer, User userDomain) {
        return recruitmentPost.scope.eq(Scope.ON_CAMPUS).and(writer.university.eq(userDomain.getUniversity()));
    }

    public static BooleanExpression categoryEq(Category category) {
        return category == null ? null : recruitmentPost.category.eq(category);
    }

    public static BooleanExpression scopeEq(Scope scope) {
        if (scope == null) {
            return null;
        } else {
            return recruitmentPost.scope.eq(scope);
        }
    }

    public static BooleanExpression titleContains(String keyword) {
        return (keyword == null || keyword.isEmpty()) ? null : recruitmentPost.title.contains(keyword);
    }

    private static BooleanExpression isFalse() {
        return Expressions.asBoolean(false);
    }

    public static BooleanExpression isWriter(ManageType manageType, User user, QUser writer) {
        return manageType.equals(ManageType.WRITTEN) ? writer.id.eq(user.getId()) : null;
    }

    public static BooleanExpression isNotDeleted() {
        return recruitmentPost.deleteStatus.ne(DeleteStatus.DELETED);
    }

}
