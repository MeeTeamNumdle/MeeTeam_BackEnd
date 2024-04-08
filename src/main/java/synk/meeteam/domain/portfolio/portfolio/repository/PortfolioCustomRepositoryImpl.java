package synk.meeteam.domain.portfolio.portfolio.repository;

import static synk.meeteam.domain.common.field.entity.QField.field;
import static synk.meeteam.domain.common.role.entity.QRole.role;
import static synk.meeteam.domain.portfolio.portfolio.entity.QPortfolio.portfolio;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.portfolio.portfolio.dto.GetProfilePortfolioDto;
import synk.meeteam.domain.portfolio.portfolio.dto.QGetProfilePortfolioDto;
import synk.meeteam.domain.portfolio.portfolio.entity.Portfolio;
import synk.meeteam.domain.user.user.entity.User;

@Repository
@RequiredArgsConstructor
public class PortfolioCustomRepositoryImpl implements PortfolioCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Portfolio> findAllByCreatedByAndIsPinTrueOrderByIds(Long userId, List<Long> portfolioIds) {

        return queryFactory
                .selectFrom(portfolio)
                .where(portfolio.id.in(portfolioIds))
                .orderBy(orderPortfolios(portfolioIds).asc())
                .fetch();
    }

    @Override
    public Slice<GetProfilePortfolioDto> findUserPortfoliosByUserOrderByCreatedAtDesc(Pageable pageable, User user) {
        List<GetProfilePortfolioDto> contents = queryFactory
                .select(new QGetProfilePortfolioDto(
                        portfolio.id,
                        portfolio.title,
                        portfolio.mainImageFileName,
                        portfolio.field.name,
                        portfolio.role.name,
                        portfolio.isPin,
                        portfolio.pinOrder
                ))
                .from(portfolio)
                .leftJoin(portfolio.role, role)
                .leftJoin(portfolio.field, field)
                .where(portfolio.isPin.eq(true),
                        portfolio.createdBy.eq(user.getId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(portfolio.createdAt.desc())
                .fetch();
        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<GetProfilePortfolioDto> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }

    NumberExpression<Integer> orderPortfolios(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }

        // 포트폴리오 ID의 위치를 기준으로 순서를 정의합니다.
        CaseBuilder caseBuilder = new CaseBuilder();

        return caseBuilder
                .when(portfolio.id.in(ids)).then(ids.indexOf(portfolio.id))
                .otherwise(ids.size());
    }

}
