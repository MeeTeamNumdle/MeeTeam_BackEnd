package synk.meeteam.domain.recruitment.recruitment_post.repository;

import static synk.meeteam.domain.recruitment.bookmark.entity.QBookmark.bookmark;
import static synk.meeteam.domain.recruitment.recruitment_applicant.entity.QRecruitmentApplicant.recruitmentApplicant;
import static synk.meeteam.domain.recruitment.recruitment_post.entity.QRecruitmentPost.recruitmentPost;
import static synk.meeteam.domain.recruitment.recruitment_post.repository.expression.ExpressionUtils.isClosedEq;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.recruitment.recruitment_post.repository.vo.QRecruitmentPostVo;
import synk.meeteam.domain.recruitment.recruitment_post.repository.vo.RecruitmentPostVo;
import synk.meeteam.domain.user.user.entity.QUser;
import synk.meeteam.domain.user.user.entity.User;

@Repository
@RequiredArgsConstructor
public class RecruitmentManagementRepositoryImpl implements RecruitmentManagementRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<RecruitmentPostVo> findMyBookmarkPost(Pageable pageable, User user, Boolean isClosed) {
        List<RecruitmentPostVo> contents = getBookmarkPostVos(pageable, user, isClosed);
        JPAQuery<Long> countQuery = getBookmarkCount(user, isClosed);
        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<RecruitmentPostVo> findMyAppliedPost(Pageable pageable, User user, Boolean isClosed) {
        List<RecruitmentPostVo> contents = getAppliedPostVos(pageable, user, isClosed);
        JPAQuery<Long> countQuery = getAppliedCount(user, isClosed);
        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<RecruitmentPostVo> findMyPost(Pageable pageable, User user, Boolean isClosed) {
        List<RecruitmentPostVo> contents = getMyPostVos(pageable, user, isClosed);
        JPAQuery<Long> countQuery = getMyPostCount(user, isClosed);
        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    private List<RecruitmentPostVo> getBookmarkPostVos(Pageable pageable, User userDomain, Boolean isClosed) {
        QUser writer = new QUser("writer");

        JPAQuery<RecruitmentPostVo> query = queryFactory.select(
                        new QRecruitmentPostVo(
                                recruitmentPost.id,
                                recruitmentPost.title,
                                recruitmentPost.category,
                                recruitmentPost.scope,
                                writer.id,
                                writer.nickname,
                                writer.profileImgFileName,
                                recruitmentPost.deadline,
                                Expressions.asBoolean(true),
                                recruitmentPost.createdAt,
                                recruitmentPost.isClosed
                        )
                )
                .distinct()
                .from(recruitmentPost)
                .leftJoin(writer).on(recruitmentPost.createdBy.eq(writer.id))
                .leftJoin(bookmark).on(recruitmentPost.id.eq(bookmark.recruitmentPost.id))
                .where(
                        isClosedEq(isClosed),
                        bookmark.user.id.eq(userDomain.getId())
                );

        return query.orderBy(recruitmentPost.createdAt.desc(), recruitmentPost.id.desc())
                .offset(pageable.getOffset()) //페이지 번호
                .limit(pageable.getPageSize()) //페이지 사이즈
                .fetch();
    }

    private List<RecruitmentPostVo> getAppliedPostVos(Pageable pageable, User userDomain, Boolean isClosed) {
        QUser writer = new QUser("writer");

        JPAQuery<RecruitmentPostVo> query = queryFactory.select(
                        new QRecruitmentPostVo(
                                recruitmentPost.id,
                                recruitmentPost.title,
                                recruitmentPost.category,
                                recruitmentPost.scope,
                                writer.id,
                                writer.nickname,
                                writer.profileImgFileName,
                                recruitmentPost.deadline,
                                Expressions.asBoolean(true),
                                recruitmentPost.createdAt,
                                recruitmentPost.isClosed
                        )
                )
                .distinct()
                .from(recruitmentPost)
                .leftJoin(writer).on(recruitmentPost.createdBy.eq(writer.id))
                .leftJoin(recruitmentApplicant).on(recruitmentPost.id.eq(recruitmentApplicant.recruitmentPost.id))
                .where(
                        isClosedEq(isClosed),
                        recruitmentApplicant.applicant.id.eq(userDomain.getId())
                );

        return query.orderBy(recruitmentPost.createdAt.desc(), recruitmentPost.id.desc())
                .offset(pageable.getOffset()) //페이지 번호
                .limit(pageable.getPageSize()) //페이지 사이즈
                .fetch();
    }

    private List<RecruitmentPostVo> getMyPostVos(Pageable pageable, User userDomain, Boolean isClosed) {
        QUser writer = new QUser("writer");

        JPAQuery<RecruitmentPostVo> query = queryFactory.select(
                        new QRecruitmentPostVo(
                                recruitmentPost.id,
                                recruitmentPost.title,
                                recruitmentPost.category,
                                recruitmentPost.scope,
                                writer.id,
                                writer.nickname,
                                writer.profileImgFileName,
                                recruitmentPost.deadline,
                                Expressions.asBoolean(true),
                                recruitmentPost.createdAt,
                                recruitmentPost.isClosed
                        )
                )
                .distinct()
                .from(recruitmentPost)
                .leftJoin(writer).on(recruitmentPost.createdBy.eq(writer.id))
                .where(
                        isClosedEq(isClosed),
                        writer.id.eq(userDomain.getId())
                );

        return query.orderBy(recruitmentPost.createdAt.desc(), recruitmentPost.id.desc())
                .offset(pageable.getOffset()) //페이지 번호
                .limit(pageable.getPageSize()) //페이지 사이즈
                .fetch();
    }

    private JPAQuery<Long> getBookmarkCount(User userDomain, Boolean isClosed) {
        QUser writer = new QUser("writer");

        return queryFactory.select(recruitmentPost.countDistinct())
                .from(recruitmentPost)
                .leftJoin(writer).on(recruitmentPost.createdBy.eq(writer.id))
                .leftJoin(bookmark).on(recruitmentPost.id.eq(bookmark.recruitmentPost.id))
                .where(
                        isClosedEq(isClosed),
                        bookmark.user.id.eq(userDomain.getId())
                );
    }

    private JPAQuery<Long> getAppliedCount(User userDomain, Boolean isClosed) {
        QUser writer = new QUser("writer");

        return queryFactory.select(recruitmentPost.countDistinct())
                .from(recruitmentPost)
                .leftJoin(writer).on(recruitmentPost.createdBy.eq(writer.id))
                .leftJoin(recruitmentApplicant).on(recruitmentPost.id.eq(recruitmentApplicant.recruitmentPost.id))
                .where(
                        isClosedEq(isClosed),
                        recruitmentApplicant.applicant.id.eq(userDomain.getId())
                );
    }

    private JPAQuery<Long> getMyPostCount(User userDomain, Boolean isClosed) {
        QUser writer = new QUser("writer");

        return queryFactory.select(recruitmentPost.countDistinct())
                .from(recruitmentPost)
                .leftJoin(writer).on(recruitmentPost.createdBy.eq(writer.id))
                .where(
                        isClosedEq(isClosed),
                        writer.id.eq(userDomain.getId())
                );
    }

}
