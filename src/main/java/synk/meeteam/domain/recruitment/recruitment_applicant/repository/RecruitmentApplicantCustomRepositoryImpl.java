package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import static synk.meeteam.domain.recruitment.recruitment_applicant.entity.QRecruitmentApplicant.recruitmentApplicant;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitStatus;

@Repository
@RequiredArgsConstructor
public class RecruitmentApplicantCustomRepositoryImpl implements RecruitmentApplicantCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long bulkApprove(List<Long> applicantIds) {
        return jpaQueryFactory.update(recruitmentApplicant)
                .where(recruitmentApplicant.id.in(applicantIds))
                .set(recruitmentApplicant.recruitStatus, RecruitStatus.APPROVED)
                .execute();
    }
}
