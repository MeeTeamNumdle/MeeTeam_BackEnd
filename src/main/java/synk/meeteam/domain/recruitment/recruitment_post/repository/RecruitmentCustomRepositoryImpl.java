package synk.meeteam.domain.recruitment.recruitment_post.repository;

import static synk.meeteam.domain.recruitment.recruitment_post.entity.QRecruitmentPost.recruitmentPost;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecruitmentCustomRepositoryImpl implements RecruitmentCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void updateIsCloseTrue() {
        queryFactory.update(recruitmentPost)
                .where(Expressions.asDate(LocalDate.now()).after(recruitmentPost.deadline))
                .set(recruitmentPost.isClosed, true)
                .execute();
    }

}
