package synk.meeteam.domain.recruitment.recruitment_post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import synk.meeteam.domain.recruitment.recruitment_post.dto.SearchCondition;
import synk.meeteam.domain.recruitment.recruitment_post.repository.vo.RecruitmentPostVo;
import synk.meeteam.domain.user.user.entity.User;

public interface RecruitmentPostSearchRepository {
    Page<RecruitmentPostVo> findBySearchConditionAndKeyword(
            Pageable pageable,
            SearchCondition condition,
            String keyword,
            User user);
}
