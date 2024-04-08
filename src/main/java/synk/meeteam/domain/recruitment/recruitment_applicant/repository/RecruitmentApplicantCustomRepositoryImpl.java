package synk.meeteam.domain.recruitment.recruitment_applicant.repository;

import static synk.meeteam.domain.common.role.entity.QRole.role;
import static synk.meeteam.domain.common.university.entity.QUniversity.university;
import static synk.meeteam.domain.recruitment.recruitment_applicant.entity.QRecruitmentApplicant.recruitmentApplicant;
import static synk.meeteam.domain.recruitment.recruitment_post.entity.QRecruitmentPost.recruitmentPost;
import static synk.meeteam.domain.user.user.entity.QUser.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import synk.meeteam.domain.common.department.entity.QDepartment;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.GetApplicantResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.dto.response.QGetApplicantResponseDto;
import synk.meeteam.domain.recruitment.recruitment_applicant.entity.RecruitStatus;

@Repository
@RequiredArgsConstructor
public class RecruitmentApplicantCustomRepositoryImpl implements RecruitmentApplicantCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long updateRecruitStatus(List<Long> applicantIds, RecruitStatus recruitStatus) {
        return jpaQueryFactory.update(recruitmentApplicant)
                .where(recruitmentApplicant.id.in(applicantIds))
                .set(recruitmentApplicant.recruitStatus, recruitStatus)
                .execute();
    }

    @Override
    public List<GetApplicantResponseDto> findByPostIdAndRoleId(Long postId, Long roleId) {

        // id, profileImg
        return jpaQueryFactory
                .select(new QGetApplicantResponseDto(recruitmentApplicant.id,
                        recruitmentApplicant.applicant.id.stringValue(),
                        recruitmentApplicant.applicant.nickname, recruitmentApplicant.applicant.profileImgFileName,
                        recruitmentApplicant.applicant.name, recruitmentApplicant.applicant.evaluationScore,
                        recruitmentApplicant.applicant.university.name, recruitmentApplicant.applicant.department.name,
                        recruitmentApplicant.applicant.admissionYear,
                        recruitmentApplicant.role.name, recruitmentApplicant.comment))
                .from(recruitmentApplicant)
                .leftJoin(recruitmentApplicant.applicant, user)
                .leftJoin(recruitmentApplicant.recruitmentPost, recruitmentPost)
                .leftJoin(recruitmentApplicant.role, role)
                .leftJoin(recruitmentApplicant.applicant.university, university)
                .leftJoin(recruitmentApplicant.applicant.department, QDepartment.department)
                .where(eqRole(roleId), recruitmentApplicant.recruitmentPost.id.eq(postId),
                        recruitmentApplicant.recruitStatus.eq(RecruitStatus.NONE))
                .fetch();
    }

    private BooleanExpression eqRole(Long roleId) {
        if (roleId == null) {
            return null;
        }
        return recruitmentApplicant.role.id.eq(roleId);
    }
}
