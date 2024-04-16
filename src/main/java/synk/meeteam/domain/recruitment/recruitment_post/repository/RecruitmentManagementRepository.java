package synk.meeteam.domain.recruitment.recruitment_post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import synk.meeteam.domain.recruitment.recruitment_post.repository.vo.RecruitmentPostVo;
import synk.meeteam.domain.user.user.entity.User;

public interface RecruitmentManagementRepository {
    Page<RecruitmentPostVo> findMyBookmarkPost(Pageable pageable, User user, Boolean isClosed);

    Page<RecruitmentPostVo> findMyAppliedPost(Pageable pageable, User user, Boolean isClosed);

    Page<RecruitmentPostVo> findMyPost(Pageable pageable, User user, Boolean isClosed);

}
